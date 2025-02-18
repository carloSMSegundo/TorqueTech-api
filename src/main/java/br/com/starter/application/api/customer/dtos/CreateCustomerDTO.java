package br.com.starter.application.api.customer.dtos;

import br.com.starter.domain.address.Address;
import br.com.starter.domain.user.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CreateCustomerDTO {
    private UserStatus status;
    private String name;
    private String document;
    private String phone;
    private LocalDate birthDate = null;
    private List<UUID> vehicleIds;
    private UUID ownerId;  // ID do usuário que está criando o cliente
    private Address address;
    private LocalDateTime createdAt = LocalDateTime.now();
}
