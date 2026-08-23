package net.hackyourfuture.tickettrackingsystem.users.dto.responces;

import net.hackyourfuture.tickettrackingsystem.enums.UserRoleEnum;

public record UserResponse(
        String userId,
        String name,
        String email,
        UserRoleEnum role) {

}
