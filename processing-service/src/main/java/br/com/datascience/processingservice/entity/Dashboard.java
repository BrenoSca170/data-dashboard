package br.com.datascience.processingservice.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard")
public class Dashboard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne
    @JoinColumn(name = "dataset_id")
    private Dataset dataset;

    // Construtor vazio
    public Dashboard() {
        this.createdAt = LocalDateTime.now();
    }

    // Construtor com argumentos
    public Dashboard(String name, String description, User creator, Dataset dataset) {
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.dataset = dataset;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public User getCreator() { return creator; }
    public Dataset getDataset() { return dataset; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setCreator(User creator) { this.creator = creator; }
    public void setDataset(Dataset dataset) { this.dataset = dataset; }
}
