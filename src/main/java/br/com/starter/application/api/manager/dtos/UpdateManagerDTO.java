package br.com.starter.application.api.manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UpdateManagerDTO {
    @Email(message = "O username deve ser um email válido")
    @NotBlank(message = "O email é obrigatório")
    private String username;
    @NotBlank(message = "A senha é obrigatória")
    private String password;
    @NotBlank(message = "O nome é obrigatório")
    private String ownerName;

    private UUID garageId;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
}
