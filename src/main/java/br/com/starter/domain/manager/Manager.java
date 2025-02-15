package br.com.starter.domain.manager;

import br.com.starter.domain.user.User;
import br.com.starter.domain.garage.Garage;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "manager")
@Getter
@Setter
public class Manager {
    @Id
    private UUID id = UUID.randomUUID();

    @OneToOne
    private Garage garage;
    @OneToOne
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
