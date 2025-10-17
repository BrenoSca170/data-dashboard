package br.com.datascience.processingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.datascience.processingservice.entity.Dataset;
import br.com.datascience.processingservice.entity.User;

import java.util.List;

public interface DatasetRepository extends JpaRepository<Dataset, Long> {
    List<Dataset> findByUser(User user); // busca todos os datasets de um usuário
}