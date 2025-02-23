package br.com.starter.domain.stockTransaction;

public enum TransactionCategory {
    SALE,                // Venda de itens para clientes
    WORK_ORDER,          // Uso de itens em ordens de serviço
    PURCHASE,            // Compra de novos itens para o estoque
    RETURN,              // Devolução de itens ao estoque
    DISPOSAL,            // Descarte de itens danificados ou sem utilidade
    TRANSFER,            // Transferência de itens entre garagens ou unidades
    WARRANTY_REPLACEMENT // Substituição de itens sob garantia
}
