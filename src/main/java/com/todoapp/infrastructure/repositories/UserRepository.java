package com.todoapp.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.infrastructure.data_mappers.UserDataMapper;

@Repository
public interface UserRepository extends JpaRepository<UserDataMapper, UUID> {

    Optional<UserDataMapper> findByUsername(String username);

    Optional<UserDataMapper> findByEmail(String email);

}
