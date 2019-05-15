package com.adiaz.springmvc.services;

import com.adiaz.springmvc.domain.security.Role;

import java.util.List;

public interface RoleService {
    List<Role> listAll();
    Role getById(Integer id);
    Role createOrUpdate(Role role);
    void delete(Integer id);
}
