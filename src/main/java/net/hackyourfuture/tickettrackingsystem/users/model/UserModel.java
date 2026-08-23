package net.hackyourfuture.tickettrackingsystem.users.model;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.hackyourfuture.tickettrackingsystem.enums.UserRoleEnum;

@Getter
@Setter
@NoArgsConstructor
public class UserModel {
    private UUID userId;
    private String name;
    private String email;
    private String passwordHash; 
    private UserRoleEnum role;   
}