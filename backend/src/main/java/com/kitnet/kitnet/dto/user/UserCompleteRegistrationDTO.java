package com.kitnet.kitnet.dto.user;

import com.kitnet.kitnet.model.enums.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserCompleteRegistrationDTO {

    @NotBlank(message = "error.validation.field.missing")
    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "error.legal.document.invalid")
    private String legalDocument;

    @NotBlank(message = "error.validation.field.missing")
    @Size(max = 20, message = "error.phone.invalid")
    private String phone;

    @NotNull(message = "error.validation.field.missing")
    private Boolean acceptTermsOfLGPD;

    @NotNull(message = "error.validation.field.missing")
    private Boolean acceptTermsOfPrivacy;

    @NotNull(message = "error.validation.field.missing")
    private Boolean authorizeCreditCheckAndCommunication;

    @NotNull(message = "error.validation.field.missing")
    private Boolean acceptMarketingCommunications;

    @Size(max = 100, message = "error.profession.invalid")
    private String profession;

    @Size(max = 200, message = "error.emergency.contact.name.invalid")
    private String emergencyContactName;

    @Size(max = 20, message = "error.emergency.contact.phone.invalid")
    private String emergencyContactPhone;

    private List<RoleName> additionalRoles;
}