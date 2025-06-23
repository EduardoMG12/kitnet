package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.EmailSendException;
import com.kitnet.kitnet.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private MessageSource messageSource;

    @Value("${app.email-from}")
    private String fromEmail;

    @Override
    public void sendEmail(String toEmail, String subject, String templateName, Map<String, Object> templateVariables) throws IOException {
        try {
            Context context = new Context();
            context.setVariables(templateVariables);
            // templateName is the name of the file (e.g. "verification_email") without the .html and without the prefix.
            String htmlContent = templateEngine.process(templateName, context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

            logger.info("Email '{}' enviado para {}. Template: {}", subject, toEmail, templateName);

        } catch (MessagingException e) {
            logger.error("Erro ao enviar e-mail com JavaMail API para {}. Assunto: {}. Erro: {}", toEmail, subject, e.getMessage(), e);
            throw new EmailSendException(messageSource.getMessage("error.email.send.failed", null, LocaleContextHolder.getLocale()), e);
        }
    }
}