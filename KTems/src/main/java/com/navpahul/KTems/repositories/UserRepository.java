package com.navpahul.KTems.repositories;

import java.util.Optional;

import com.navpahul.KTems.entities.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findById(Long id);
    public Optional<User> findByUsernameOrEmail(String username, String email);
    public Optional<User> findByUsername(String username);
}