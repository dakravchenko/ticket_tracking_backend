package net.hackyourfuture.tickettrackingsystem.users.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import net.hackyourfuture.tickettrackingsystem.users.dto.requests.CreateUserRequest;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;
import net.hackyourfuture.tickettrackingsystem.users.services.UserService;

@RestController
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserModel> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@Valid @RequestBody CreateUserRequest user) {
        UserModel createdUser = userService.createUser(user.name(), user.email());
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(UUID id) {
        UserModel user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
