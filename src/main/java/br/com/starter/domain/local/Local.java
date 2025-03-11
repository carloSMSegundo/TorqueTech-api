package br.com.starter.domain.local;

import br.com.starter.domain.garage.Garage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "local")
@Getter
@Setter
public class Local {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private LocalStatus status = LocalStatus.ACTIVE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdAt = LocalDateTime.now();
}
