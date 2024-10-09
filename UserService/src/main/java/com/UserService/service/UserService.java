package com.UserService.service;

import com.UserService.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findById(long userId);

    List<User> getAll();
}
