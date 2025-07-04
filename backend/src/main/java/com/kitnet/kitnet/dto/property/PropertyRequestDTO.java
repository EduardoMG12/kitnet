package com.kitnet.kitnet.dto.property;

import com.kitnet.kitnet.model.enums.PropertyPurpose;
import com.kitnet.kitnet.model.enums.PropertyType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PropertyRequestDTO {
    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String title;

    @NotBlank(message = "A descrição não pode estar em branco")
    private String description;

    @NotNull(message = "O tipo do imóvel é obrigatório")
    private PropertyType type;

    @NotNull(message = "A finalidade do imóvel é obrigatória")
    private PropertyPurpose purpose;

    @DecimalMin(value = "0.00", message = "O preço de aluguel deve ser maior ou igual a zero")
    private BigDecimal rentalPrice;

    @DecimalMin(value = "0.00", message = "O preço de venda deve ser maior ou igual a zero")
    private BigDecimal salePrice;

    private Boolean showRentalPrice = true;
    private Boolean showSalePrice = true;

    @Pattern(regexp = "^\\d{8}$", message = "O CEP deve conter 8 dígitos numéricos")
    private String zipCode;

    @NotBlank(message = "O estado é obrigatório")
    @Size(max = 100, message = "O estado deve ter no máximo 100 caracteres")
    private String state;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres")
    private String city;

    @NotBlank(message = "O bairro é obrigatório")
    @Size(max = 100, message = "O bairro deve ter no máximo 100 caracteres")
    private String neighborhood;

    @NotBlank(message = "O endereço da rua é obrigatório")
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    private String addressStreet;

    @Size(max = 255, message = "O complemento do endereço deve ter no máximo 255 caracteres")
    private String addressComplement;

    @NotNull(message = "A ocultação do endereço exato é obrigatória")
    private Boolean hideExactAddress = false;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotNull(message = "A área em metros quadrados é obrigatória")
    @DecimalMin(value = "0.00", message = "A área em metros quadrados deve ser maior ou igual a zero")
    private BigDecimal squareMeters;

    @DecimalMin(value = "0.00", message = "A área construída deve ser maior ou igual a zero")
    private BigDecimal builtArea;

    @Min(value = 0, message = "O número de quartos deve ser maior ou igual a zero")
    private Integer bedrooms;

    @Min(value = 0, message = "O número de banheiros deve ser maior ou igual a zero")
    private Integer bathrooms;

    @Min(value = 0, message = "O número de vagas de estacionamento deve ser maior ou igual a zero")
    private Integer parkingSpaces;

    @NotNull(message = "A aceitação de pets é obrigatória")
    private Boolean acceptsPets = false;

    private String amenities;

    @Min(value = 0, message = "O andar deve ser maior ou igual a zero")
    private Integer floor;

    @DecimalMin(value = "0.00", message = "A taxa de condomínio deve ser maior ou igual a zero")
    private BigDecimal condominiumFee;

    @NotNull(message = "A disponibilidade é obrigatória")
    private Boolean isAvailable = true;

    @NotNull(message = "A confirmação do proprietário é obrigatória")
    private Boolean ownerConfirmation;

    @NotNull(message = "A aceitação dos termos de anúncio é obrigatória")
    private Boolean termsAgreement;


    @NotNull(message = "A permissão para outros agentes é obrigatória")
    private Boolean allowOtherAgents = false;

    @Min(value = 0, message = "O máximo de agentes permitidos deve ser maior ou igual a zero")
    private Integer maxAllowedAgents = 0;

    @DecimalMin(value = "0.00", message = "A porcentagem de comissão deve ser maior ou igual a zero")
    @Max(value = 100, message = "A porcentagem de comissão deve ser no máximo 100")
    private BigDecimal brokerageFeePercentage;
}