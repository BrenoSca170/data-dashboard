// Local: src/main/java/br/com/datascience/processingservice/service/DataProcessingService.java
package br.com.datascience.processingservice.service;

import br.com.datascience.processingservice.dto.AnalysisResult;
import br.com.datascience.processingservice.dto.ColumnInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

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

        Map<String, Object> summaryMap = new HashMap<>();
        for (Row row : summary) {
            String key = row.getString(0) + " (" + row.getString(1) + ")";
            Object value = row.getObject(2);
            summaryMap.put(key, value);
        }

        // ==================================================================
        // INÍCIO DA NOVA LÓGICA: Estatísticas Detalhadas por Coluna
        // ==================================================================
        Map<String, Map<String, Object>> detailedStats = new HashMap<>();
        for (Column<?> column : dataTable.columns()) {
            Map<String, Object> statsForColumn = new HashMap<>();
            
            // Adiciona a contagem de valores únicos para todas as colunas
            statsForColumn.put("uniqueValues", column.unique().size());

            // Se a coluna for numérica, calcula estatísticas adicionais
            if (column instanceof NumericColumn) {
                NumericColumn<?> numericColumn = (NumericColumn<?>) column;
                statsForColumn.put("median", numericColumn.median());
                statsForColumn.put("stdDeviation", numericColumn.standardDeviation());
                statsForColumn.put("sum", numericColumn.sum());
            }
            
            detailedStats.put(column.name(), statsForColumn);
        }
        // ==================================================================
        // FIM DA NOVA LÓGICA
        // ==================================================================

        return AnalysisResult.builder()
                .fileName(fileName)
                .rowCount(rowCount)
                .columnDetails(columnDetails)
                .summaryStatistics(summaryMap)
                .detailedStatistics(detailedStats) // <-- ADICIONA OS NOVOS DADOS À RESPOSTA
                .build();
    }
}