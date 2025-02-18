package br.com.starter.application.api.common;

import br.com.starter.domain.user.UserStatus;
import lombok.Data;

@Data
public class GetPageRequest {
    private Integer size = 10;
    private String query = "";
    private UserStatus status = UserStatus.ACTIVE;
}
