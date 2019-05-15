package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.User;

import java.util.List;

public interface UserService {

    List<User> listAll();
    User getById(Integer id);
    User createOrUpdate(User user);
    void delete(Integer id);

}

