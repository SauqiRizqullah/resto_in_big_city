package com.upgrade.resto.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Object getByUserId(String id);

    Object getByContext();
}
