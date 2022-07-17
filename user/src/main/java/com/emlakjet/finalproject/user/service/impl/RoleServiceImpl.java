package com.emlakjet.finalproject.user.service.impl;

import com.emlakjet.finalproject.user.dao.RoleRepository;
import com.emlakjet.finalproject.user.entity.Role;
import com.emlakjet.finalproject.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean createRole(Role role) {
        try {
            roleRepository.save(role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteRole(Long id) {
        try {
            roleRepository.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateRole(Role role) {
        try {
            Role newRole = roleRepository.getById(role.getId());
            newRole.setName(role.getName());
            roleRepository.save(newRole);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role getAdmin() {
        return roleRepository.getByName("ADMIN");
    }

    @Override
    public Role getUser() {
        return roleRepository.getByName("USER");
    }
}
