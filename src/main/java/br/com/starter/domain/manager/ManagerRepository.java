package br.com.starter.domain.manager;

import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {

    Optional<Manager> findByUser(User user);

    @Query("""
        SELECT manager FROM Manager manager
        WHERE manager.garage.id = :garageId
        AND manager.user.status = :status
        AND (
            :query IS NULL
            OR LOWER(manager.user.profile.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """) //Entender esse parametro para usar em vehicleRepository
    Page<Manager> findPageByStatusAndNames(
        @Param("garageId") UUID garageId,
        @Param("query") String query,
        @Param("status") UserStatus status,
        Pageable pageable
    );

    // findByIdAndGarageId
}
