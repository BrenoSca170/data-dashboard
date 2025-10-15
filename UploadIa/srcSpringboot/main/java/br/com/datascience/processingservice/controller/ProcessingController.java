// Local: src/main/java/br/com/datascience/processingservice/controller/ProcessingController.java
package br.com.datascience.processingservice.controller;

import br.com.datascience.processingservice.dto.AnalysisResult;
import br.com.datascience.processingservice.service.DataProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProcessingController {

    private final DataProcessingService dataProcessingService;

    public ProcessingController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @PostMapping("/process-data")
    public ResponseEntity<AnalysisResult> processData(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            AnalysisResult result = dataProcessingService.analyze(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}