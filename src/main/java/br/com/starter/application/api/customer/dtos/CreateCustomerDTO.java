package br.com.starter.application.api.customer.dtos;

import br.com.starter.domain.user.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateCustomerDTO {
    private UserStatus status;
    private UUID profileId;
    private UUID garageId;
    private UUID ownerId;
    private LocalDateTime createdAt = LocalDateTime.now(); // Armazena a data e hora de criação do cliente, com um valor padrão no momento da criação
}
