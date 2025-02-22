package br.com.starter.domain.work;

import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.user.User;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.workOrder.WorkOrder;
import br.com.starter.domain.workOrder.WorkOrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "work")
@Getter
@Setter
public class Work {
    @Id
    private UUID id = UUID.randomUUID();

    private String title;
    private String description;
    private Long totalCost;
    private Long price;

    @Enumerated(EnumType.STRING)
    private WorkStatus status = WorkStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = true)
    private Mechanic mechanic;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "garage_id", nullable = false)
    private Garage garage;

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<WorkOrder> orders = new HashSet<>();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt; // dúvida - usar o LocalDateTime.now()???
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime concludedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expectedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
