package com.upgrade.resto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<Object, String> {
    Optional<Object> findByUsername(String id);
}
