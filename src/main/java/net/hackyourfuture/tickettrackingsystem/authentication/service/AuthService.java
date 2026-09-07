package net.hackyourfuture.tickettrackingsystem.authentication.service;

import net.hackyourfuture.tickettrackingsystem.authentication.dto.AuthResponse;
import net.hackyourfuture.tickettrackingsystem.authentication.dto.LoginRequest;
import net.hackyourfuture.tickettrackingsystem.authentication.dto.RegisterRequest;
import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;
import net.hackyourfuture.tickettrackingsystem.users.dto.UserResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            UserDao userDao
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userDao.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        String passwordHash = passwordEncoder.encode(request.password());

        UserResponse createdUser = userDao.createUserWithPassword(
                request.name(),
                request.email(),
                passwordHash
        );

        userDao.addRole(createdUser.userId(), "USER");
        
        //and uuthenticate immediately after registration
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}