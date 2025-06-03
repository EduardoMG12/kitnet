package com.kitnet.kitnet.model;

import com.kitnet.kitnet.model.enums.LegalDocumentType;
import com.kitnet.kitnet.model.enums.LegalPersonType;
import com.kitnet.kitnet.model.enums.RoleName;
import com.kitnet.kitnet.model.enums.VerificationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "O nome/razão social não pode estar em branco")
    @Size(max = 200, message = "O nome/razão social deve ter no máximo 200 caracteres")
    @Column(nullable = false)
    private String name;

    @Pattern(regexp = "^\\d{11}$|^\\d{14}$", message = "O documento legal deve conter 11 (CPF) ou 14 (CNPJ) dígitos numéricos")
    @Column(unique = true, nullable = true)
    private String legalDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private LegalPersonType legalPersonType;

    @NotBlank(message = "O email não pode estar em branco")
    @Email(message = "Formato de email inválido")
    @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres")
    @Column(nullable = true)
    private String phone;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserLegalDocument> userLegalDocuments = new HashSet<>();

    @NotNull(message = "A autorização para verificação de crédito e comunicação é obrigatória")
    @Column(nullable = false)
    private Boolean authorizeCreditCheckAndCommunication;

    @Column(nullable = false)
    private Boolean acceptMarketingCommunications = false;

    @Size(max = 500, message = "A URL da foto de perfil deve ter no máximo 500 caracteres")
    private String profilePictureUrl;

    @Column(nullable = true, length = 100)
    private String profession;

    @Column(nullable = true, length = 200)
    private String emergencyContactName;

    @Column(nullable = true, length = 20)
    private String emergencyContactPhone;

    // --- Relação para roles ---
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus accountVerificationStatus = VerificationStatus.NOT_SUBMITTED;

    private Double monthlyGrossIncome;
    private Boolean hasCreditRestrictions = false;
    private Boolean isIdentityConfirmed = false;
    private Boolean isEmailVerified = false;
    private Boolean isPhoneVerified = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(java.util.stream.Collectors.toSet());
    }

    public boolean hasRole(RoleName roleName) {
        return this.roles.stream().anyMatch(role -> role.getName() == roleName);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userLegalDocuments.stream()
                .anyMatch(doc -> doc.getLegalDocument().getType() == LegalDocumentType.TERMS_OF_USE);
    }
}
