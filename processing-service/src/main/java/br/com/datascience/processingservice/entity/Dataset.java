package br.com.datascience.processingservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "dataset")
public class Dataset implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "file_path", length = 255, nullable = false)
    private String filePath;

    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DatasetStatus status = DatasetStatus.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Construtor vazio
    public Dataset() {
        this.uploadDate = LocalDateTime.now();
    }

    // Construtor com argumentos
    public Dataset(String name, String filePath, User user) {
        this.name = name;
        this.filePath = filePath;
        this.user = user;
        this.uploadDate = LocalDateTime.now();
        this.status = DatasetStatus.PENDENTE;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getFilePath() { return filePath; }
    public LocalDateTime getUploadDate() { return uploadDate; }
    public DatasetStatus getStatus() { return status; }
    public User getUser() { return user; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setUploadDate(LocalDateTime uploadDate) { this.uploadDate = uploadDate; }
    public void setStatus(DatasetStatus status) { this.status = status; }
    public void setUser(User user) { this.user = user; }

    // Enum para status
    public enum DatasetStatus {
        PROCESSADO,
        ERRO,
        PENDENTE
    }
}
