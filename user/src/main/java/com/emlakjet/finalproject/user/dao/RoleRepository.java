package com.emlakjet.finalproject.user.dao;

import com.emlakjet.finalproject.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}
