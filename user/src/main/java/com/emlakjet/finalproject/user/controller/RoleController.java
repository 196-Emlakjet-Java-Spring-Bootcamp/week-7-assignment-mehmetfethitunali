package com.emlakjet.finalproject.user.controller;

import com.emlakjet.finalproject.user.entity.Role;
import com.emlakjet.finalproject.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        if (roleService.createRole(role)){
            return new ResponseEntity("Role Created.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Role Not Created.",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Role> updateRole(@RequestBody Role role){
        if (roleService.updateRole(role)){
            return new ResponseEntity("Role Updated.",HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Role Not Updated.",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable Long id){
        if (roleService.deleteRole(id)){
            return new ResponseEntity("Role Deleted.",HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Role Not Deleted.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Role> getRole(@PathVariable Long id){
        return new ResponseEntity(roleService.getRole(id),HttpStatus.OK);
    }
}
