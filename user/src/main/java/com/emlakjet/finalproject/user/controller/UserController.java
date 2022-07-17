package com.emlakjet.finalproject.user.controller;

import com.emlakjet.finalproject.user.dto.JwtAuthenticationResponse;
import com.emlakjet.finalproject.user.dto.LoginModel;
import com.emlakjet.finalproject.user.dto.UserDto;
import com.emlakjet.finalproject.user.dto.UserSummary;
import com.emlakjet.finalproject.user.entity.User;
import com.emlakjet.finalproject.user.security.CurrentUser;
import com.emlakjet.finalproject.user.security.JwtTokenProvider;
import com.emlakjet.finalproject.user.security.UserPrincipal;
import com.emlakjet.finalproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginModel loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        System.out.println(jwt);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        if(currentUser == null){
            return new ResponseEntity("Login to get access",HttpStatus.UNAUTHORIZED);
        }
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getAuthorities().stream().findFirst().get().getAuthority());
        return new ResponseEntity( userSummary, HttpStatus.OK);
    }

    @GetMapping("/meAdmin")
    public UserSummary getCurrentAdmin(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),
                currentUser.getAuthorities().stream().findFirst().get().getAuthority());
        return userSummary;
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<Boolean> isAuthenticated() {
        return new ResponseEntity(true,HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto){
        userDto.getUser().setPassword(bCryptPasswordEncoder.encode(userDto.getUser().getPassword()));
        if (userService.createUser(userDto)){
            return new ResponseEntity("User Created.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity("User Not Created.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto){
        userDto.getUser().setPassword(bCryptPasswordEncoder.encode(userDto.getUser().getPassword()));
        if (userService.updateUser(userDto)){
            return new ResponseEntity("User Updated.", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("User Not Updated.", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody UserDto userDto){
        if (userService.deleteUser(userDto)) {
            return new ResponseEntity("User Deleted.", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("User Not Deleted.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        return new ResponseEntity(userService.getUser(id),HttpStatus.OK);
    }

    @GetMapping("/usersummary/{id}")
    public ResponseEntity<UserSummary> transferUserSummary(@PathVariable Long id){
        return new ResponseEntity(userService.transferUserSummary(id),HttpStatus.ACCEPTED);
    }
}
