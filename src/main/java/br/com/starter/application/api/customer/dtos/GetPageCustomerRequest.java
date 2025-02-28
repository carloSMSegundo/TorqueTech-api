package br.com.starter.application.api.customer.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class GetPageCustomerRequest {
    private Integer size = 10; // Tamanho da página
    private Set<String> carPlates; // Filtro por placa do carro
    private String title; // Filtro por título
    private String status; // Filtro por status
    private LocalDate expectedDate; // Filtro por data esperada
}
