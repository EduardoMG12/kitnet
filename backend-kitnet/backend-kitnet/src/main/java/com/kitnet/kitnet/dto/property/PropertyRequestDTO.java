package com.kitnet.kitnet.dto.property;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDTO {

    private UUID ownerId;

    @NotBlank(message = "O tipo de propriedade não pode estar em branco")
    private String propertyType;

    @NotBlank(message = "O título do anúncio não pode estar em branco")
    @Size(max = 255, message = "O título do anúncio deve ter no máximo 255 caracteres")
    private String adTitle;

    private String description;

    @NotBlank(message = "A finalidade não pode estar em branco")
    private String purpose;

    private Double rentValue;

    private String zipCode;

    private String state;

    private String city;

    private String neighborhood;

    private String address;

    private String number;

    private String complement;

    private Boolean hideExactAddress;

    private Double squareMeters;

    private Double builtArea;

    private Integer bedrooms;

    private Integer bathrooms;

    private Integer parkingSpaces;

    private String amenities;

    private Integer floor;

    private Double condominiumFee;

    private String photos;

    @NotNull(message = "A confirmação de proprietário é obrigatória")
    private Boolean ownerConfirmation;

    @NotNull(message = "A aceitação dos termos é obrigatória")
    private Boolean termsAgreement;
}
