package br.com.starter.application.api.garage.dtos;

import br.com.starter.application.api.common.AddressDTO;
import br.com.starter.domain.role.RoleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateGarageDTO {
    private String name;
    private String cnpj;

    private String username;
    private String password;
    private String ownerName;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private RoleType role = RoleType.ROLE_ADMIN;

    private AddressDTO address = null;
}
