package com.kitnet.kitnet.repository;

import com.kitnet.kitnet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    // Você pode adicionar outros métodos de consulta personalizados aqui, se necessário.
}