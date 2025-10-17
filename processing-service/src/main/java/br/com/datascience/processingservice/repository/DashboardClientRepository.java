package br.com.datascience.processingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.datascience.processingservice.entity.DashboardClient;
import br.com.datascience.processingservice.entity.User;

import java.util.List;

public interface DashboardClientRepository extends JpaRepository<DashboardClient, Long> {
    List<DashboardClient> findByClient(User client);
}
