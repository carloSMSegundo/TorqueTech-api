package br.com.starter.domain.mechanic;

import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, UUID> {

    Optional<Mechanic> findByUser(User user);

    @Query("""
        SELECT mechanic FROM Mechanic mechanic
        WHERE mechanic.garage.id = :garageId
        AND mechanic.user.status = :status
        AND (
            :query IS NULL
            OR LOWER(mechanic.user.profile.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Page<Mechanic> findPageByStatusAndNames(
            @Param("garageId") UUID garageId,
            @Param("query") String query,
            @Param("status") UserStatus status,
            Pageable pageable
    );

    @Query("""
        SELECT mechanic FROM Mechanic mechanic
        WHERE mechanic.id = :id
        AND mechanic.garage.id = :garageId
    """)
    Optional<Mechanic> findByIdAndGarageId(
            @Param("id") UUID id,
            @Param("garageId") UUID garageId
    );

    @Query("""
        SELECT mechanic FROM Mechanic mechanic
        WHERE mechanic.garage.id = :garageId
    """)
    List<Mechanic> findByGarageId(
        @Param("garageId") UUID garageId
    );

    int countByGarageId(UUID garageId);
}
