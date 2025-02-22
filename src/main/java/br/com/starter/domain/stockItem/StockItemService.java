package br.com.starter.domain.stockItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockItemService {
    private final StockItemRepository stockItemRepository;

    public StockItem save(StockItem stockItem) {
        return stockItemRepository.save(stockItem);
    }


}
