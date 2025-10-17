package br.com.datascience.processingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.datascience.processingservice.entity.Dashboard;
import br.com.datascience.processingservice.entity.User;

import java.util.List;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    List<Dashboard> findByCreator(User creator);
}