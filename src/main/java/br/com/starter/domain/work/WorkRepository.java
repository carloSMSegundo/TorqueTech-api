package br.com.starter.domain.work;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface WorkRepository extends JpaRepository<Work, UUID> {

    @Query("""
        SELECT w FROM Work w
        WHERE w.id IN :ids
    """)
    Page<Work> findAllByIds(
            @Param("ids") Set<UUID> ids,
            Pageable pageable
    );

    @Query("""
    SELECT w.id FROM Work w
    JOIN w.vehicle v
    WHERE v.licensePlate IN :licensePlates
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByLicensePlates(
            @Param("licensePlates") Set<String> licensePlates,
            @Param("garageId") UUID garageId
    );

    @Query("""
    SELECT w.id FROM Work w
    WHERE LOWER(w.title) LIKE LOWER(CONCAT('%', :title, '%'))
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByTitleFilter(
            @Param("title") String title,
            @Param("garageId") UUID garageId
    );

    @Query("""
    SELECT w.id FROM Work w
    WHERE w.status = :status
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByStatusFilter(
            @Param("status") WorkStatus status,
            @Param("garageId") UUID garageId
    );

    @Query("""
    SELECT w.id FROM Work w
    WHERE w.expectedAt = :expectedAt
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByExpectedAt(
            @Param("expectedAt") LocalDateTime expectedAt,
            @Param("garageId") UUID garageId
    );


    @Query("""
    SELECT w.id FROM Work w
    WHERE w.customer.id = :customerId
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByCustomerId(
            @Param("customerId") UUID customerId,
            @Param("garageId") UUID garageId
    );

    @Query("""
    SELECT w.id FROM Work w
    WHERE w.mechanic.id = :mechanicId
    AND w.garage.id = :garageId
    """)
    Set<UUID> findByMechanicId(
            @Param("mechanicId") UUID customerId,
            @Param("garageId") UUID garageId
    );

}

