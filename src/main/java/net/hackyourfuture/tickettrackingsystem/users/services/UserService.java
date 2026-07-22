package net.hackyourfuture.tickettrackingsystem.users.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;
import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@Service
@Getter
@Setter
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserModel> getUsers() {
        return userDao.findAllUsers();
    }

    public UserModel getUserById(UUID id) {
        return userDao.findById(id);
    }

    public UserModel createUser(String name, String email) {
        return userDao.createUser(name, email);
    }

}
