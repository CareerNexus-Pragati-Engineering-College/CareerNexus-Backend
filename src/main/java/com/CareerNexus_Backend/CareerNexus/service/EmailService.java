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

    /**
     * Sends a professional HTML invitation for the exam slot.
     */
    public void sendExamInvite(String toEmail, String studentName, String jobTitle, String roundName,String slotStart, String slotEnd) {
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
                    <p>Please log in to your dashboard during the active slot to start the test. The exam link will only be visible during this time.</p>
                    <br>
                    <p>Regards,<br><b>Career Nexus Team</b></p>
                </div>
                """.formatted(studentName, jobTitle,roundName, slotStart, slotEnd);

            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email dispatch failed for: " + toEmail + " Error: " + e.getMessage());
        }
    }
}