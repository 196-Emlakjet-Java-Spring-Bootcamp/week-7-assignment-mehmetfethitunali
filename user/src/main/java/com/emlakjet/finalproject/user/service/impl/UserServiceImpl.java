package com.emlakjet.finalproject.user.service.impl;

import com.emlakjet.finalproject.user.dao.UserRepository;
import com.emlakjet.finalproject.user.dto.UserDto;
import com.emlakjet.finalproject.user.dto.UserSummary;
import com.emlakjet.finalproject.user.entity.Role;
import com.emlakjet.finalproject.user.entity.User;
import com.emlakjet.finalproject.user.service.RoleService;
import com.emlakjet.finalproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;


    @Override
    public boolean createUser(UserDto userDto) {
        try {
            userDto.getUser().setCreatedAt(new Date());
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getUser());
            if (userDto.isAdmin()){
                roles.add(roleService.getAdmin());
            }
            userDto.getUser().setRoles(roles);
            userRepository.save(userDto.getUser());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(UserDto userDto) {
        try {
            userRepository.deleteById(userDto.getUser().getId());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        try{
            User oldUser = userRepository.getReferenceById(userDto.getUser().getId());
            oldUser.setName(userDto.getUser().getName());
            oldUser.setSurname(userDto.getUser().getSurname());
            oldUser.setPassword(userDto.getUser().getPassword());
            oldUser.setEmail(userDto.getUser().getEmail());
            oldUser.setPhoneNumber(userDto.getUser().getPhoneNumber());
            oldUser.setRoles(userDto.getUser().getRoles());
            oldUser.setUpdatedAt(new Date());
            userRepository.save(oldUser);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSummary transferUserSummary(Long id){
        User user = userRepository.getById(id);
        UserSummary userSummary = new UserSummary();
        userSummary.setUsername(user.getUsername());
        userSummary.setName(user.getName());
        userSummary.setId(user.getId());
        return userSummary;
    }
}
