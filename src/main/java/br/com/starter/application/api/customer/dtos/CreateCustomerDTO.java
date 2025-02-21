package br.com.starter.application.api.customer.dtos;

import br.com.starter.application.api.common.AddressDTO;
import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.domain.user.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCustomerDTO {
    private UserStatus status = UserStatus.ACTIVE;
    private String name;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private AddressDTO address = null;
    private List<CreateVehicleDTO> vehicles;
}
