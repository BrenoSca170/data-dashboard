
// Local: src/main/java/br/com/datascience/processingservice/service/DataProcessingService.java
package br.com.datascience.processingservice.service;

import br.com.datascience.processingservice.dto.AnalysisResult;
import br.com.datascience.processingservice.dto.ColumnInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {

    public AnalysisResult analyze(MultipartFile file) throws IOException {
        Table dataTable = Table.read().csv(file.getInputStream());

        int rowCount = dataTable.rowCount();
        String fileName = file.getOriginalFilename();

        List<ColumnInfo> columnDetails = dataTable.columns().stream()
                .map(column -> ColumnInfo.builder()
                        .name(column.name())
                        .type(column.type().name())
                        .build())
                .collect(Collectors.toList());

        Table summary = dataTable.summary();

        // ==================================================================
        // INÍCIO DA CORREÇÃO: Usando índices de coluna (0, 1, 2)
        // ==================================================================
        Map<String, Object> summaryMap = new HashMap<>();
        for (Row row : summary) {
            // A estrutura da tabela de sumário é:
            // Coluna 0: A estatística (ex: "Mean")
            // Coluna 1: A coluna original (ex: "Idade")
            // Coluna 2: O valor calculado (ex: 31.5)
            String key = row.getString(0) + " (" + row.getString(1) + ")";
            Object value = row.getObject(2);
            summaryMap.put(key, value);
        }
        // ==================================================================
        // FIM DA CORREÇÃO
        // ==================================================================

        return AnalysisResult.builder()
                .fileName(fileName)
                .rowCount(rowCount)
                .columnDetails(columnDetails)
                .summaryStatistics(summaryMap)
                .build();
    }
}