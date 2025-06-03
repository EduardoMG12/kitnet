package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.exception.EmailSendException;
import com.kitnet.kitnet.service.EmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
//@Profile("!prod") // Simulação apenas em não-produção
public class EmailServiceImpl implements EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${app.email-from:kitnet.rent@hotmail.com}")
    private String fromEmail;

    @Value("${sendgrid.template-id:d-8bf6365d3ea2483a9e3c2b7ca0081b31}")
    private String templateId;

    @Override
    public void sendVerificationEmail(String toEmail, String userName, String subjectType, String verificationLink) throws IOException {

        try {
            Email from = new Email(fromEmail);
            Email to = new Email(toEmail);
            String subject = "Verifique seu E-mail - Kitnet";

            Mail mail = new Mail();
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.setTemplateId(templateId);

            Personalization personalization = new Personalization();
            personalization.addTo(to);
            personalization.addDynamicTemplateData("user_name", userName != null ? userName : "Usuário");
            personalization.addDynamicTemplateData("verification_link", verificationLink);
            mail.addPersonalization(personalization);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

        } catch (IOException e) {
            throw new EmailSendException("Erro ao enviar e-mail de verificação para " + toEmail, e);
        } catch (Exception e) {
            throw new EmailSendException("Erro inesperado ao enviar e-mail para " + toEmail, e);
        }
    }
}