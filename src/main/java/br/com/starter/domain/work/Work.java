package br.com.starter.domain.work;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "work")
@Getter
@Setter
public class Work {
    @Id
    private UUID id = UUID.randomUUID();

    // adicionar o resto depois
}
