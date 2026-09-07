package net.hackyourfuture.tickettrackingsystem.users.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;
import net.hackyourfuture.tickettrackingsystem.users.dto.UserResponse;

@Service
@Getter
@Setter
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> getUsers() {
        return userDao.findAllUsers();
    }

    public UserResponse getUserById(UUID id) {
        return userDao.findById(id);
    }

    public UserResponse createUser(String name, String email) {
        return userDao.createUser(name, email);
    }

    public UserResponse updateUser(UUID id, String name, String email) {
        return userDao.updateUser(id, name, email);
    }

    public boolean deleteUser(UUID id) {
        return userDao.deleteUser(id);
    }

}
