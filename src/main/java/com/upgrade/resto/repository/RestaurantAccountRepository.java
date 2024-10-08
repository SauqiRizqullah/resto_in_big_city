package com.upgrade.resto.repository;

import com.upgrade.resto.entity.RestaurantAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantAccountRepository extends JpaRepository<RestaurantAccount, String> {
    Optional<RestaurantAccount> findByUsername(String username);
}
