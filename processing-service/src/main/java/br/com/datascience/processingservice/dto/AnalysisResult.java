package br.com.datascience.processingservice.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AnalysisResult {
    private String fileName;
    private int rowCount;
    private List<ColumnInfo> columnDetails;
    private Map<String, Object> summaryStatistics;
    private Map<String, Map<String, Object>> detailedStatistics; // <-- ADICIONE ESTA LINHA
}