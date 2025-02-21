package br.com.starter.application.api.customer.dtos;

import br.com.starter.application.api.common.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateCustomerDTO {
    @NotBlank(message = "O nome não pode estar em branco")
    private String name;
    @Size(min = 11, max = 14, message = "CPF/CNPj inválido")
    private String document;
    @Size(min = 8, max = 11, message = "Telefone inválido")
    private String phone;
    private LocalDate birthDate = null;
    private AddressDTO address = null;
}
