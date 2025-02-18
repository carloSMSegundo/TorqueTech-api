package br.com.starter.application.api.user.dto;

import br.com.starter.domain.role.RoleType;
import br.com.starter.domain.user.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserRegistrationRequest {
    @Email(message = "O username deve ser um email válido")
    @NotBlank(message = "O email é obrigatório")
    private String username;
    @NotBlank(message = "A senha é obrigatória")
    private String password;
    @NotBlank(message = "O nome é obrigatório")
    private String name;

    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private UserStatus status = null;
    private RoleType role = RoleType.ROLE_SUPER_ADMIN;
}