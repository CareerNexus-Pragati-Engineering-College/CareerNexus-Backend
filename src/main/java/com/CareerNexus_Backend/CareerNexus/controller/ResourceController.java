package com.CareerNexus_Backend.CareerNexus.controller;

import com.CareerNexus_Backend.CareerNexus.dto.ResourceResponseDTO;
import com.CareerNexus_Backend.CareerNexus.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "*")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    // ── Upload ───────────────────────────────────────────────────────────────

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadResource(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("year") String year,
            @RequestParam("semester") String semester,
            @RequestParam("regulation") String regulation,
            @RequestParam("branch") String branch,
            @RequestParam(value = "resourceLink", required = false) String resourceLink,
            @RequestParam("userId") String userId) {

        try {
            ResourceResponseDTO response = resourceService.uploadResource(
                    file, title, description,
                    year, semester, regulation, branch,
                    resourceLink, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));

        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "File upload failed. Please try again."));
        }
    }

    // ── Get All Resources (Paginated) ─────────────────────────────────────────

    @GetMapping
    public ResponseEntity<Page<ResourceResponseDTO>> getAllResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(resourceService.getAllResourcesPaginated(pageable));
    }

    // ── Get All Resources (Simple List) ──────────────────────────────────────

    @GetMapping("/all")
    public ResponseEntity<List<ResourceResponseDTO>> getAllResourcesList() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    // ── Get My Uploads ────────────────────────────────────────────────────────

    @GetMapping("/my-uploads")
    public ResponseEntity<?> getMyUploads(@RequestParam("userId") String userId) {
        try {
            return ResponseEntity.ok(resourceService.getMyUploads(userId));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResource(
            @PathVariable Long id,
            @RequestParam("userId") String userId) {

        try {
            resourceService.deleteResource(id, userId);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}