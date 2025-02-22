package br.com.starter.domain.stockTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, UUID> {

   List<StockTransaction> findAllById(Set<UUID> ids);
   Page<StockTransaction> findAllById(Set<UUID> ids, Pageable pageable);

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.stockItem.item.id in :itemIds
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
        @Param("ownerId") Set<UUID> ownerId
    );

    @Query("""
        SELECT distinct s.id FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND s.transactionType = :type
    """)
    Set<UUID> findBytransactionTypeFilter(
        @Param("garageId") UUID garageId,
        @Param("type") TransactionType type
    );

    @Query("""
        SELECT s FROM StockTransaction s
        WHERE s.garage.id = :garageId
        AND (
            :query IS NULL
            OR LOWER(s.stockItem.item) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    List<StockTransaction> findByItemNameFilters(
        @Param("garageId") UUID garageId,
        @Param("query") String query
    );
}
