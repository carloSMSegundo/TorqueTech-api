package br.com.starter.domain.stockItem;

import br.com.starter.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockItemRepository extends JpaRepository<StockItem, UUID> {

    List<StockItem> findAllByItem(Item item);
}
