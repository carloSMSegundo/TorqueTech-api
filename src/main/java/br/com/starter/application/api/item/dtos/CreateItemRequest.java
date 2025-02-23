package br.com.starter.application.api.item.dtos;

import lombok.Data;

@Data
public class CreateItemRequest {
    private String name;
    private String description;
    private String category;
}
