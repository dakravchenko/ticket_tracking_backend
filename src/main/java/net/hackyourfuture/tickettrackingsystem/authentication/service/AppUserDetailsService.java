package net.hackyourfuture.tickettrackingsystem.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public AppUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        UserModel user = userDao.findByUserEmail(email);

        if (user == null) {
            throw new RuntimeException("Something went wrong");
        }

        var roles = userDao.findRolesByUserId(user.getUserId());

        var authorities = roles.stream()
                .map(r -> "ROLE_" + r)
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}