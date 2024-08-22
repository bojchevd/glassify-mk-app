package com.example.glassify.web;

import com.example.glassify.model.dto.ContactForm;
import com.example.glassify.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utility")
@CrossOrigin(origins ="http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class UtilityController {

    @Autowired
    private EmailService emailService;

    private static final Logger log = LoggerFactory.getLogger(UtilityController.class);

    @PostMapping("/sendMail")
    public Integer sendContactEmail(@RequestBody ContactForm contactForm) {
        log.info("Received email: " + contactForm.getName() + " said: " + contactForm.getMessage());
        String subject = "New Contact Form Submission";
        String text = "Name: " + contactForm.getName() + "\nEmail: " + contactForm.getEmail() + "\nMessage: " + contactForm.getMessage();
        emailService.sendEmail("glassifymk@gmail.com", subject, text);
        return 1;
    }
}
