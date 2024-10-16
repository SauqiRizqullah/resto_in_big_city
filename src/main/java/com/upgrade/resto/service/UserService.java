package com.upgrade.resto.service;

import com.upgrade.resto.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserAccount getByUserId(String id);

    UserAccount getByContext();
}
