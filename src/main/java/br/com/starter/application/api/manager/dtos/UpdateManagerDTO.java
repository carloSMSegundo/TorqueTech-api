package br.com.starter.application.api.manager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateManagerDTO {
    private String username;
    private String password;
    private String name;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
}
