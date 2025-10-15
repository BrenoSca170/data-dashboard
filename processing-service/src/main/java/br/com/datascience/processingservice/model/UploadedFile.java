// Crie o arquivo: src/main/java/br/com/datascience/processingservice/model/UploadedFile.java
package br.com.datascience.processingservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_files")
@Data
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private Long size;
    
    private LocalDateTime uploadTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}