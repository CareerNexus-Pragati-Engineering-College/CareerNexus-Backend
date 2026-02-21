package com.CareerNexus_Backend.CareerNexus.service;

import com.CareerNexus_Backend.CareerNexus.dto.ResourceResponseDTO;
import com.CareerNexus_Backend.CareerNexus.exceptions.InvalidFileException;
import com.CareerNexus_Backend.CareerNexus.exceptions.ResourceNotFoundException;
import com.CareerNexus_Backend.CareerNexus.exceptions.UnauthorizedActionException;
import com.CareerNexus_Backend.CareerNexus.model.Resource;
import com.CareerNexus_Backend.CareerNexus.model.Student;
import com.CareerNexus_Backend.CareerNexus.repository.ResourceRepository;
import com.CareerNexus_Backend.CareerNexus.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private SupabaseStorageService storageService;

    @Autowired
    private StudentRepository studentRepository;

    // ── Upload ───────────────────────────────────────────────────────────────

    public ResourceResponseDTO uploadResource(
            MultipartFile file,
            String title,
            String description,
            String year,
            String semester,
            String regulation,
            String branch,
            String resourceLink,
            String userId) throws IOException {

        // file is optional if resourceLink is provided
        if ((file == null || file.isEmpty()) && (resourceLink == null || resourceLink.isBlank())) {
            throw new InvalidFileException("Please upload a PDF or provide a YouTube/Drive link");
        }

        // Only validate file if one is actually provided
        if (file != null && !file.isEmpty()) {
            validateFile(file);
        }

        // Upload to Supabase only if file is present
        String fileUrl = null;
        String fileName = null;
        Long fileSize = null;

        if (file != null && !file.isEmpty()) {
            fileUrl = storageService.uploadFile(file);
            fileName = file.getOriginalFilename();
            fileSize = file.getSize();
        }

        // Find student
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with userId: " + userId));

        // Build and save Resource entity
        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setDescription(description);
        resource.setYear(year);
        resource.setSemester(semester);
        resource.setRegulation(regulation);
        resource.setBranch(branch);
        resource.setResourceLink(resourceLink);
        resource.setFileName(fileName);
        resource.setFileUrl(fileUrl);
        resource.setFileSize(fileSize);
        resource.setUploadedBy(student);

        Resource saved = resourceRepository.save(resource);
        return mapToDTO(saved);
    }

    // ── Get All Resources (Paginated) ─────────────────────────────────────────

    public Page<ResourceResponseDTO> getAllResourcesPaginated(Pageable pageable) {
        return resourceRepository
                .findAllByOrderByUploadedAtDesc(pageable)
                .map(this::mapToDTO);
    }

    // ── Get All Resources (Simple List) ──────────────────────────────────────

    public List<ResourceResponseDTO> getAllResources() {
        return resourceRepository.findAllByOrderByUploadedAtDesc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── Get My Uploads ────────────────────────────────────────────────────────

    public List<ResourceResponseDTO> getMyUploads(String userId) {
        return resourceRepository
                .findByUploadedByUserIdOrderByUploadedAtDesc(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    public void deleteResource(Long resourceId, String userId) {

        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Resource not found with id: " + resourceId));

        if (!resource.getUploadedBy().getUserId().equals(userId)) {
            throw new UnauthorizedActionException(
                    "Unauthorized: You can only delete your own uploads");
        }

        // Only delete from Supabase if a file was actually uploaded
        if (resource.getFileUrl() != null) {
            storageService.deleteFile(resource.getFileUrl());
        }

        resourceRepository.delete(resource);
    }

    // ── File Validation ───────────────────────────────────────────────────────

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("File is empty or missing");
        }
        if (!"application/pdf".equals(file.getContentType())) {
            throw new InvalidFileException("Only PDF files are allowed");
        }
        if (file.getSize() > 10L * 1024 * 1024) {
            throw new InvalidFileException("File size must be under 10MB");
        }
    }

    // ── Entity → DTO Mapper ───────────────────────────────────────────────────

    private ResourceResponseDTO mapToDTO(Resource resource) {
        ResourceResponseDTO dto = new ResourceResponseDTO();

        dto.setId(resource.getId());
        dto.setTitle(resource.getTitle());
        dto.setDescription(resource.getDescription());
        dto.setFileName(resource.getFileName());
        dto.setFileUrl(resource.getFileUrl());
        dto.setFileSizeFormatted(formatFileSize(resource.getFileSize()));

        // New fields
        dto.setYear(resource.getYear());
        dto.setSemester(resource.getSemester());
        dto.setRegulation(resource.getRegulation());
        dto.setBranch(resource.getBranch());
        dto.setResourceLink(resource.getResourceLink());

        Student uploader = resource.getUploadedBy();
        dto.setUploaderName(uploader.getFirstName() + " " + uploader.getLastName());
        dto.setUploadedById(uploader.getUserId());

        dto.setUploadedAt(resource.getUploadedAt() != null
                ? resource.getUploadedAt().toString()
                : "");

        return dto;
    }

    // ── File Size Formatter ───────────────────────────────────────────────────

    private String formatFileSize(Long bytes) {
        if (bytes == null) return "Unknown";
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return (bytes / 1024) + " KB";
        return String.format("%.1f MB", bytes / (1024.0 * 1024));
    }
}