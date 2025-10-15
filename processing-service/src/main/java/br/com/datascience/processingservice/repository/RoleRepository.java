package br.com.datascience.processingservice.repository;

import br.com.datascience.processingservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Encontra uma Role pelo seu nome.
     * O Spring Data JPA cria a implementação deste método automaticamente
     * com base no nome do método.
     *
     * @param name O nome da role a ser procurada (ex: "USER" ou "ADMIN").
     * @return um Optional contendo a Role se encontrada, ou vazio caso contrário.
     */
    Optional<Role> findByName(String name);
}