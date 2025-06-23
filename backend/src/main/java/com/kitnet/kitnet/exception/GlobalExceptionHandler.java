package com.kitnet.kitnet.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.security.authentication.BadCredentialsException; // Importar esta classe

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), messageSource.getMessage(error.getDefaultMessage(), null, error.getDefaultMessage(), locale)));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.resource.not.found", null, "Resource not found.", locale);
        return new ResponseEntity<>(errorMessage + ": " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleCustomResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.already.in.use", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatchException(PasswordMismatchException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.password.mismatch", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.user.not.found", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.credentials", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.credentials", null, "Credenciais inválidas.", locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(org.springframework.web.server.ResponseStatusException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String reason = ex.getReason();

        String errorMessage;
        if (reason != null && messageSource.getMessage(reason, null, reason, locale) != null) {
            errorMessage = messageSource.getMessage(reason, null, reason, locale);
        } else {
            errorMessage = (reason != null) ? reason : ex.getMessage();
        }
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.internal.runtime", null, "Ocorreu um erro interno: " + ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.unexpected", null, "Ocorreu um erro inesperado: " + ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TermsNotAcceptedException.class)
    public ResponseEntity<String> handleTermsNotAcceptedException(TermsNotAcceptedException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.terms.of.use.required", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<String> handleEmailNotVerifiedException(EmailNotVerifiedException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.not.verified", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.request.body",
                new Object[]{ex.getLocalizedMessage()},
                "Invalid request body: " + ex.getLocalizedMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<Map<String, String>> handleEmailSendException(EmailSendException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Failed to send email");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyVerifiedException.class)
    public ResponseEntity<String> handleEmailAlreadyVerifiedException(EmailAlreadyVerifiedException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.already.verified", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // Retorne o mesmo status que no @ResponseStatus
    }

    @ExceptionHandler(VerificationEmailAlreadySentException.class)
    public ResponseEntity<String> handleVerificationEmailAlreadySentException(VerificationEmailAlreadySentException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.verification.already.sent", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(FirebaseAuthenticationException.class)
    public ResponseEntity<String> handleFirebaseAuthenticationException(FirebaseAuthenticationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.firebase.auth.invalid.token", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<String> handleInternalServerErrorException(InternalServerErrorException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.internal.server.operation.failed", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<String> handleInvalidOperationException(InvalidOperationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.operation", null, ex.getMessage(), locale);
        // Ou use a própria mensagem da exceção se ela já for a chave localizada, como você fez em outros lugares
        // errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        // Assume que a mensagem da exceção já contém a chave do MessageSource ou é a mensagem final
        String errorMessage = messageSource.getMessage("error.role.not.found", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND); // Role não encontrada
    }

    @ExceptionHandler(UnauthorizedRoleAssignmentException.class)
    public ResponseEntity<String> handleUnauthorizedRoleAssignmentException(UnauthorizedRoleAssignmentException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.access.denied", null, ex.getMessage(), locale);
        // Ou uma chave mais específica como "error.role.assign.unauthorized" se quiser ser granular.
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN); // Acesso negado para atribuir role
    }

    @ExceptionHandler(InvalidRoleOperationException.class)
    public ResponseEntity<String> handleInvalidRoleOperationException(InvalidRoleOperationException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        // A mensagem da exceção deve ser clara (ex: "A role padrão LESSEE não pode ser removida")
        String errorMessage = messageSource.getMessage("error.invalid.role.operation", null, ex.getMessage(), locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST); // Operação inválida na role
    }
}
