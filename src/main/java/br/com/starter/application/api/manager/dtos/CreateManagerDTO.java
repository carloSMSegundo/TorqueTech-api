package br.com.starter.application.api.manager.dtos;

import br.com.starter.domain.role.RoleType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateManagerDTO {
    private String username;
    private String password;
    private String ownerName;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private RoleType role = RoleType.ROLE_MANAGER;
}
