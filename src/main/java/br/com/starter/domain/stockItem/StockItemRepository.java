package br.com.starter.domain.stockItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
        AND s.id = :id
    """)
    Optional<StockItem>  findByIdAndGarageId(
        @Param("garageId") UUID garageId,
        @Param("id") UUID id
    );
}
