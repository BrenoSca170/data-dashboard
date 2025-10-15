// Crie o arquivo: src/main/java/br/com/datascience/processingservice/repository/UploadedFileRepository.java
package br.com.datascience.processingservice.repository;

import br.com.datascience.processingservice.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, Long> {
}