package com.upgrade.resto.repository;

import com.upgrade.resto.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);
}
