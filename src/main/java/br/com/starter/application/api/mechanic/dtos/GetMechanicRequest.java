package br.com.starter.application.api.mechanic.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMechanicRequest {
    private String query;
    private boolean sortByCreatedAt;
}
