package br.com.starter.domain.vehicle;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    @Query("""
        SELECT v FROM Vehicle v
        WHERE LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :licensePlate, '%'))
    """)
    Page<Vehicle> findPageByLicensePlate(
            @Param("licensePlate") String licensePlate,
            Pageable pageable
    );

    @Query("""
    SELECT v FROM Vehicle v
    WHERE v.customer.id = :customerId
    AND v.customer.garage.id = :garageId
    AND (
        :licensePlate IS NULL
        OR LOWER(v.licensePlate) LIKE LOWER(CONCAT('%', :licensePlate, '%'))
    )
""")
    Page<Vehicle> findByCustomerAndGarage(
            @Param("customerId") UUID customerId,
            @Param("garageId") UUID garageId,
            @Param("licensePlate") String licensePlate,
            Pageable pageable
    );

}
