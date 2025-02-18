package br.com.starter.application.api.garage.dtos;

import br.com.starter.application.api.common.AddressDTO;
import br.com.starter.domain.role.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UpdateGarageDTO {
    private String name;
    private String cnpj;

    @Email(message = "O username deve ser um email válido")
    @NotBlank(message = "O email é obrigatório")
    private String username;
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    @NotBlank(message = "O nome é obrigatório")
    private String ownerName;
    private UUID ownerId;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private AddressDTO address = null;
}
