package com.kitnet.kitnet.service;

import java.io.IOException;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String userName, String subjectType, String verificationLink) throws IOException;
}