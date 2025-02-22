package br.com.starter.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("""
        SELECT i FROM Item i
        WHERE i.garage.id = :garageId
        AND i.status = :status
        AND (
            :query IS NULL
            OR LOWER(i.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    List<Item> findAllByGarageAndQuery(
        @Param("garageId") UUID garageId,
        @Param("status") ItemStatus status,
        @Param("query") String query
    );
}
