package br.com.starter.application.api.customer.dtos;

import br.com.starter.application.api.vehicle.dtos.CreateVehicleDTO;
import br.com.starter.application.api.vehicleType.dto.CreateVehicleTypeDTO;
import br.com.starter.domain.address.Address;
import br.com.starter.domain.user.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateCustomerDTO {
    private UserStatus status = UserStatus.ACTIVE;
    private String name;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private Address address;
    private List<CreateVehicleDTO> vehicles;
}
