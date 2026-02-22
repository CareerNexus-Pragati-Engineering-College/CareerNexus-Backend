package com.CareerNexus_Backend.CareerNexus.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.display-name}")
    private String senderName;

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    /**
     * Sends a professional HTML invitation for the exam slot.
     */
    public void sendExamInvite(String toEmail, String studentId, String studentName, String jobTitle, String roundName, String slotStart, String slotEnd, Long assessmentId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(toEmail);
            helper.setSubject("Assessment Invitation: " + jobTitle);

            // Professional HTML Template
            String htmlBody = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #eee; padding: 20px;">
                    <div style="background-color: #004a99; color: white; padding: 10px; text-align: center; border-radius: 5px 5px 0 0;">
                        <h2>Career Nexus Assessment</h2>
                    </div>
                    <p>Dear <b>%s</b>,</p>
                    <p>An assessment has been scheduled for the position of <b>%s</b>.</p>
                    <p> Round Name <b>%s</b>.</p>
                    <div style="background-color: #f9f9f9; padding: 15px; border-left: 5px solid #004a99; margin: 20px 0;">
                        <p><strong>Date & Time Slot:</strong></p>
                        <p>Start: %s</p>
                        <p>End: %s</p>
                    </div>
                    
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="%s" style="background-color: #004a99; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: 600; font-size: 15px; display: inline-block;">Start Assessment</a>
                    </div>
                    
                    <p>Please click the button above and log in to your dashboard during the active slot to start the test. The test will only be accessible during the specified time window.</p>
                    <br>
                    <p>Regards,<br><b>Career Nexus Team</b></p>
                </div>
                """.formatted(studentName, jobTitle,roundName, slotStart, slotEnd, frontendUrl + "/student/" + studentId + "/test/" + assessmentId);

            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email dispatch failed for: " + toEmail + " Error: " + e.getMessage());
        }
    }

    /**
     * Sends a professional HTML confirmation receipt when a student applies for a job.
     */
    public void sendApplicationConfirmationEmail(String toEmail, String studentName, String companyName, String jobTitle, String location) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress(senderEmail, senderName));
            helper.setTo(toEmail);
            helper.setSubject("Application Received: " + jobTitle + " at " + companyName);

            // Dynamic frontend link based on environment
            String applicationsLink = frontendUrl + "/student/applications";

            String htmlBody = """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 12px rgba(0,0,0,0.1);">
                    <div style="background: linear-gradient(135deg, #004a99, #00234b); color: white; padding: 25px 20px; text-align: center;">
                        <h2 style="margin: 0; font-size: 24px; font-weight: 600;">Application Received</h2>
                        <p style="margin: 10px 0 0; opacity: 0.9; font-size: 14px;">Career Nexus platform</p>
                    </div>
                    
                    <div style="padding: 30px 20px; background-color: #ffffff; color: #333;">
                        <p style="font-size: 16px; line-height: 1.6;">Dear <b>%s</b>,</p>
                        
                        <p style="font-size: 16px; line-height: 1.6;">Great news! Your application for the <b>%s</b> position at <b>%s</b> has been successfully submitted to the recruitment team.</p>
                        
                        <div style="background-color: #f4f7f6; padding: 20px; border-radius: 6px; margin: 25px 0;">
                            <h3 style="margin-top: 0; font-size: 18px; color: #004a99; border-bottom: 2px solid #e0e0e0; padding-bottom: 10px;">Application Summary</h3>
                            <p style="margin: 10px 0;"><strong>Company:</strong> %s</p>
                            <p style="margin: 10px 0;"><strong>Role:</strong> %s</p>
                            <p style="margin: 10px 0;"><strong>Location:</strong> %s</p>
                            <p style="margin: 10px 0;"><strong>Status:</strong> <span style="background-color: #e3f2fd; color: #1976d2; padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 12px;">SUBMITTED</span></p>
                        </div>
                        
                        <p style="font-size: 15px; line-height: 1.6; color: #555;">The recruitment team will review your profile and reach out to you if you are shortlisted for the next rounds of the hiring process. You will be notified via email of any scheduled assessments.</p>
                        
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="%s" style="background-color: #004a99; color: white; padding: 12px 24px; text-decoration: none; border-radius: 4px; font-weight: 600; font-size: 15px; display: inline-block;">View My Applications</a>
                        </div>
                        
                        <p style="font-size: 15px; margin-top: 30px; border-top: 1px solid #eee; padding-top: 20px;">
                            Best wishes for your career,<br>
                            <strong style="color: #004a99;">Career Nexus Team</strong>
                        </p>
                    </div>
                </div>
                """.formatted(studentName, jobTitle, companyName, companyName, jobTitle, location != null ? location : "Not Specified", applicationsLink);

            helper.setText(htmlBody, true);
            mailSender.send(message);
            System.out.println("Dispatched Application Confirmation Email to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Application Confirmation Email dispatch failed for: " + toEmail + " Error: " + e.getMessage());
        }
    }
}