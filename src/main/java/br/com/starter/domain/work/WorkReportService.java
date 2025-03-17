package br.com.starter.domain.work;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkReportService {
    private final WorkService workService;

    public byte[] generateWorkReport(UUID workId) {
        // Busca o Work pelo ID
        Work work = workService.getById(workId)
                .orElseThrow(() -> new IllegalArgumentException("Work não encontrado!"));

        // Formata os detalhes do Work em texto
        String report = """
                ================================
                RELATÓRIO DO TRABALHO
                ================================
                ID: %s
                Título: %s
                Descrição: %s
                Status: %s
                Custo Total: R$ %s
                Criado em: %s
                ================================
                """.formatted(
                work.getId(), work.getTitle(), work.getDescription(),
                work.getStatus(), work.getPrice(), work.getCreatedAt()
        );

        // Retorna o texto em formato de bytes
        return report.getBytes(StandardCharsets.UTF_8);
    }
}
