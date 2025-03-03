package br.com.starter.application.api.customer.dtos;

import br.com.starter.application.api.common.AddressDTO;
import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.domain.user.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCustomerDTO {
    private UserStatus status = UserStatus.ACTIVE;
    @NotBlank(message = "O nome não pode estar em branco")
    private String name;
    @Size(min = 11, max = 14, message = "CPF/CNPj inválido")
    private String document;
    private String phone;
    private String email;
    private LocalDate birthDate = null;
    private AddressDTO address = null;
    private List<CreateVehicleDTO> vehicles;
}
