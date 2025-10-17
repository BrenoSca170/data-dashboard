package br.com.datascience.processingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.datascience.processingservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // exemplo de método customizado
}

