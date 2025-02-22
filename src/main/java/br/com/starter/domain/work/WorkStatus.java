package br.com.starter.domain.work;

public enum WorkStatus {
    PENDING,          // Serviço criado, aguardando início
    IN_PROGRESS,      // Serviço em andamento
    WAITING_PARTS,    // Aguardando peças ou materiais
    ON_HOLD,          // Pausado por algum motivo (aguardando aprovação do cliente, pagamento, etc.)
    COMPLETED,        // Serviço concluído
    CANCELED,         // Serviço cancelado
    DELIVERED,        // Veículo foi entregue ao cliente
    INVOICED,         // Fatura gerada, aguardando pagamento
    PAID,             // Pagamento efetuado
    CANCELLED,        // Serviço cancelado
}