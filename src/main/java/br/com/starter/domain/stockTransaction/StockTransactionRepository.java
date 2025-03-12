package br.com.starter.domain.stockTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, UUID> {

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.id in :ids
    """)
    List<StockTransaction> findAllByIds(
        @Param("ids") Set<UUID> ids
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.id in :ids
        AND s.type = :type
    """)
    Page<StockTransaction> findAllByIds(
        @Param("ids") Set<UUID> ids,
        @Param("type") TransactionType type,
        Pageable pageable
    );

    @Query("""
        SELECT s.id FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.id in :id
    """)
    Optional<StockTransaction> findByIdAndGarageId(
        @Param("garageId") UUID garageId,
        @Param("id") UUID id
    );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        JOIN s.items i
        WHERE s.garage.id = :garageId
        AND i.stockItem.item.id in :itemIds
    """)
   Set<UUID> findByItemsFilter(
        @Param("garageId") UUID garageId,
       @Param("itemIds") Set<UUID> itemIds
   );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.owner.id = :ownerId
    """)
    Set<UUID> findByOwnerFilter(
        @Param("garageId") UUID garageId,
        @Param("ownerId") UUID ownerId
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.type = :type
    """)
    Page<StockTransaction> findAllByGarageAndType(
        @Param("garageId") UUID garageId,
        @Param("type") TransactionType type,
        Pageable pageable
    );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.category = :category
    """)
    Set<UUID> findByTransactionCategoryFilter(
        @Param("garageId") UUID garageId,
        @Param("category") TransactionCategory category
    );

    @Query("""
        SELECT distinct s.id  FROM StockTransaction s
        JOIN s.items i
        WHERE s.garage.id = :garageId
        AND (
            :query IS NULL
            OR LOWER(i.stockItem.item.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Set<UUID> findByItemNameFilters(
        @Param("garageId") UUID garageId,
        @Param("query") String query
    );
}
