package com.kitnet.kitnet.exception;

import com.kitnet.kitnet.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.security.authentication.BadCredentialsException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        Locale locale = LocaleContextHolder.getLocale();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorMessageKey = Objects.requireNonNullElse(error.getDefaultMessage(), "error.validation.default");

            Object[] args = {error.getField()};

            String defaultMessage = messageSource.getMessage("error.validation.default", args, "Validation failed for field: " + error.getField(), locale);
            String finalMessage = messageSource.getMessage(errorMessageKey, args, defaultMessage, locale);
            fieldErrors.put(error.getField(), finalMessage);
        });

        String mainMessage = messageSource.getMessage("error.validation.message", null, "Validation failed for one or more fields.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                mainMessage,
                request.getRequestURI(),
                fieldErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.resource.not.found", null, "Resource not found.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                errorMessage + ": " + ex.getMessage(),
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.already.in.use", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(PasswordMismatchException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.password.mismatch", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.user.not.found", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.credentials", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.credentials", null, "Credenciais inválidas.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
//        Locale locale = LocaleContextHolder.getLocale();
//        String reason = ex.getReason();
//        String errorMessage = (reason != null) ? messageSource.getMessage(reason, null, reason, locale) : ex.getMessage();
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.resolve(ex.getStatusCode().value()),
//                errorMessage,
//                request.getRequestURI(),
//                null);
//
//        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.internal.runtime", null, "Ocorreu um erro interno: " + ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorMessage,
                request.getRequestURI(),
                null);

        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.unexpected", null, "Ocorreu um erro inesperado: " + ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorMessage,
                request.getRequestURI(),
                null);

        ex.printStackTrace();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TermsNotAcceptedException.class)
    public ResponseEntity<ErrorResponse> handleTermsNotAcceptedException(TermsNotAcceptedException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.terms.of.use.required", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleEmailNotVerifiedException(EmailNotVerifiedException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.not.verified", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.request.body", new Object[]{ex.getLocalizedMessage()}, "Invalid request body: " + ex.getLocalizedMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendException.class)
    public ResponseEntity<ErrorResponse> handleEmailSendException(EmailSendException ex, HttpServletRequest request) {
        String errorMessage = "Failed to send email: " + ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailAlreadyVerifiedException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyVerifiedException(EmailAlreadyVerifiedException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.already.verified", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerificationEmailAlreadySentException.class)
    public ResponseEntity<ErrorResponse> handleVerificationEmailAlreadySentException(VerificationEmailAlreadySentException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.email.verification.already.sent", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.TOO_MANY_REQUESTS,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(FirebaseAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleFirebaseAuthenticationException(FirebaseAuthenticationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.firebase.auth.invalid.token", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.internal.server.operation.failed", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidOperationException(InvalidOperationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.operation", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.role.not.found", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedRoleAssignmentException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedRoleAssignmentException(UnauthorizedRoleAssignmentException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.access.denied", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidRoleOperationException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRoleOperationException(InvalidRoleOperationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.invalid.role.operation", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.file.upload.failed", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFileFormatException(InvalidFileFormatException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.file.format.invalid", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeExceededException(FileSizeExceededException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.file.size.exceeded", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String maxFileSizeFormatted = (ex.getMaxUploadSize() / (1024 * 1024)) + "MB";
        String errorMessage = messageSource.getMessage("error.file.size.exceeded", new Object[]{maxFileSizeFormatted}, "File size exceeds limit.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.PAYLOAD_TOO_LARGE,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(MissingServletRequestPartException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.file.empty", null, "File is required.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDocumentNotFoundException(DocumentNotFoundException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.document.not.found", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedOperationException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedOperationException(UnauthorizedOperationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.document.upload.unauthorized", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DocumentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleDocumentAlreadyExistsException(DocumentAlreadyExistsException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.document.already.exists", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LegalDocumentAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleLegalDocumentAlreadyExistsException(LegalDocumentAlreadyExistsException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.legal.document.in.use", null, ex.getMessage(), locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DocumentValidationException.class)
    public ResponseEntity<ErrorResponse> handleDocumentValidationException(DocumentValidationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("error.document.validation.failed", new Object[]{ex.getMessage()}, "Document validation failed.", locale);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getRequestURI(),
                null);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}