package br.com.starter.application.api.item.dtos;

import br.com.starter.domain.item.ItemStatus;
import lombok.Data;

@Data
public class UpdateItemStatusRequest {
    private ItemStatus status;
}
