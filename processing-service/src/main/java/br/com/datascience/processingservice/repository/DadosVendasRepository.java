package br.com.datascience.processingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.datascience.processingservice.entity.DadosVendas;
import br.com.datascience.processingservice.entity.Dataset;

import java.util.List;

public interface DadosVendasRepository extends JpaRepository<DadosVendas, Long> {
    List<DadosVendas> findByDataset(Dataset dataset);
}
