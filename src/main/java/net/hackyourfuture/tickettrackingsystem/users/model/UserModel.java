package net.hackyourfuture.tickettrackingsystem.users.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    private String name;

    private String email;
}
