package com.upgrade.resto.repository;

import com.upgrade.resto.entity.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, String> {
    Optional<Waiter> findByUsername(String username);
}
