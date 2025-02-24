package br.com.starter.domain.local;

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
public interface LocalRepository extends JpaRepository<Local, UUID> {

    @Query("""
        SELECT l FROM Local l
        WHERE l.garage.id = :garageId
        AND l.status = :status
    """)
    List<Local>  findAllByGarageAndQuery(
        @Param("garageId") UUID garageId,
        @Param("status") LocalStatus status
    );

    @Query("""
        SELECT l FROM Local l
        WHERE l.garage.id = :garageId
        AND l.status = :status
        AND (
            :query IS NULL
            OR LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Page<Local> findAllByGarageAndQuery(
        @Param("garageId") UUID garageId,
        @Param("status") LocalStatus status,
        @Param("query") String query,
        Pageable pageable
    );

    @Query("""
        SELECT l FROM Local l
        WHERE l.garage.id = :garageId
        AND l.id = :id
    """)
    Optional<Local> findByIdAndGarageId(
        @Param("garageId") UUID garageId,
        @Param("id") UUID id
    );
}
