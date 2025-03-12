package br.com.starter.domain.stockItem;

import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.stockTransaction.TransactionType;
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
public interface StockItemRepository extends JpaRepository<StockItem, UUID> {

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.garage.id = :garageId
        AND s.quantity > 0
    """)
    List<StockItem> findAllWithItems(
        @Param("garageId") UUID garageId
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.garage.id = :garageId
        AND s.item.id = :itemId
        AND s.acquisitionPrice = :acquisitionPrice
        AND s.local.id = :localId
    """)
    Optional<StockItem> findByItemAndAcquisitionPriceAndStatus(
        @Param("itemId") UUID itemId,
        @Param("garageId") UUID garageId,
        @Param("localId") UUID localId,
        @Param("acquisitionPrice") Long acquisitionPrice
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.garage.id = :garageId
    """)
    Page<StockItem> findAllByGarageId(
        @Param("garageId") UUID garageId,
        Pageable pageable
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.garage.id = :garageId
        AND s.id in :ids
    """)
    Page<StockItem> findAllByIds(
        @Param("garageId") UUID garageId,
        @Param("ids") Set<UUID> ids,
        Pageable pageable
    );

    @Query("""
        SELECT s FROM StockItem s
        WHERE s.garage.id = :garageId
        AND s.id = :id
    """)
    Optional<StockItem>  findByIdAndGarageId(
        @Param("garageId") UUID garageId,
        @Param("id") UUID id
    );

    @Query("""
        SELECT COUNT(DISTINCT s.item) FROM StockItem s
        WHERE s.garage.id = :garageId
    """)
    int countDistinctItemsByGarageId(@Param("garageId") UUID garageId);

    @Query("""
        SELECT COALESCE(SUM(s.quantity), 0) FROM StockItem s
        WHERE s.garage.id = :garageId
    """)
    int sumQuantityByGarageId(@Param("garageId") UUID garageId);

    @Query("""
        SELECT distinct s.id FROM StockItem s
        WHERE s.garage.id = :garageId
        AND s.item.id in :itemIds
    """)
    Set<UUID> findByItemsFilter(
        @Param("garageId") UUID garageId,
        @Param("itemIds") Set<UUID> itemIds
    );

    @Query("""
        SELECT distinct s.id  FROM StockItem s
        WHERE s.garage.id = :garageId
        AND (
            :query IS NULL
            OR LOWER(s.item.name) LIKE LOWER(CONCAT('%', :query, '%'))
        )
    """)
    Set<UUID> findByItemNameFilters(
        @Param("garageId") UUID garageId,
        @Param("query") String query
    );
}
