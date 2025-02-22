package br.com.starter.domain.stockTransaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockTransactionService {
    private final StockTransactionRepository stockTransactionRepository;

    public StockTransaction save(StockTransaction stockTransaction) {
        return stockTransactionRepository.save(stockTransaction);
    }
}
