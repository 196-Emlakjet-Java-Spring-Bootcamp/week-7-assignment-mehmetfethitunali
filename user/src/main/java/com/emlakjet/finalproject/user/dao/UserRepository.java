package com.emlakjet.finalproject.user.dao;

import com.emlakjet.finalproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByUsername(String username);
}
