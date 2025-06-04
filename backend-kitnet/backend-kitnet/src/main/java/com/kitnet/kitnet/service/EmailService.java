package com.kitnet.kitnet.service;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String templateName, Map<String, Object> templateVariables) throws IOException;
}