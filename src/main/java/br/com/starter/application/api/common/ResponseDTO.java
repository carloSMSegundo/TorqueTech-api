package br.com.starter.application.api.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
public class ResponseDTO <T> implements Serializable {
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime time = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    public ResponseDTO(T data) {
        this.data = data;
    }
}
