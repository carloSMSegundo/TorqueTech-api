package br.com.starter.domain.item;

import br.com.starter.domain.garage.Garage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "item")
@Getter
@Setter
public class Item {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;
    private String category;
    private String description;

    @Enumerated(EnumType.STRING)
    private ItemStatus status = ItemStatus.ACTIVE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}