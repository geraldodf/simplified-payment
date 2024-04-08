package com.desafio.repositories;

import com.desafio.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public UserDetails findByEmail(String email);
    public Optional<User> findByDocument(String document);
    public Optional<User> findByName(String document);
}
