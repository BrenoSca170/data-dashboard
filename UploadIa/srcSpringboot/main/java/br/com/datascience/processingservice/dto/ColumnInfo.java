// Local: src/main/java/br/com/datascience/processingservice/dto/ColumnInfo.java
package br.com.datascience.processingservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnInfo {
    private String name;
    private String type;
}