package com.emlakjet.finalproject.user.service;

import com.emlakjet.finalproject.user.entity.Role;

import java.util.Optional;

public interface RoleService {

    boolean createRole(Role role);

    boolean deleteRole(Long id);

    boolean updateRole(Role role);

    Optional<Role> getRole(Long id);

    Role getAdmin();

    Role getUser();

}
