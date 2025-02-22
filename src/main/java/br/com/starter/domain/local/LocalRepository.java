package br.com.starter.domain.local;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocalRepository extends JpaRepository<Local, UUID> {

    @Query("""
        SELECT l FROM Local l
        WHERE l.garage.id = :garageId
        AND (
            :query IS NULL
            OR LOWER(l.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    List<Local>  findAllByGarageAndQuery(
        @Param("garageId") UUID garageId,
        @Param("query") String query
    );
}
