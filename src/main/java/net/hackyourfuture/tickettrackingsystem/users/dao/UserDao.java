package net.hackyourfuture.tickettrackingsystem.users.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import net.hackyourfuture.tickettrackingsystem.users.dto.UserResponse;
import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@RegisterConstructorMapper(UserResponse.class)
@RegisterBeanMapper(UserModel.class)
public interface UserDao {

    @SqlQuery("""
                SELECT *
                FROM users
                WHERE user_id = :id
            """)
    UserResponse findById(@Bind("id") UUID id);

    @SqlQuery("""
            SELECT *
            FROM users
                """)
    List<UserResponse> findAllUsers();

    @SqlQuery("""
                INSERT INTO users (name, email)
                VALUES (:name, :email)
                RETURNING user_id, name, email
            """)
    UserResponse createUser(
            @Bind("name") String name,
            @Bind("email") String email);

    @SqlQuery("""
                UPDATE users
                SET name = :name, email = :email
                WHERE user_id = :id
                RETURNING user_id, name, email
            """)
    UserResponse updateUser(
            @Bind("id") UUID id,
            @Bind("name") String name,
            @Bind("email") String email);

    @SqlUpdate("""
                DELETE FROM users
                WHERE user_id = :id
            """)
    boolean deleteUser(@Bind("id") UUID id);

    @SqlQuery("""
                SELECT *
                FROM users
                WHERE email = :email
            """)
    UserModel findByUserEmail(@Bind("email") String email);

    @SqlQuery("""
            SELECT password_hash
            FROM users
            WHERE email = :email
            """)
    Optional<String> findPasswordHashByEmail(@Bind("email") String email);

    @SqlQuery("""
                INSERT INTO users (name, email, password_hash)
                VALUES (:name, :email, :passwordHash)
                RETURNING
                  user_id,
                  name,
                  email
            """)
    UserResponse createUserWithPassword(
            @Bind("name") String name,
            @Bind("email") String email,
            @Bind("passwordHash") String passwordHash);

    @SqlUpdate("""
            INSERT INTO user_roles (user_id, role)
            VALUES (:userId, :role)
            """)
    void addRole(
            @Bind("userId") UUID userId,
            @Bind("role") String role);

    @SqlQuery("""
                SELECT
                  user_id,
                  name,
                  email
                FROM users
                WHERE email = :email
            """)
    Optional<UserResponse> findByEmail(@Bind("email") String email);

    @SqlQuery("""
                SELECT role
                FROM user_roles
                WHERE user_id = :userId
            """)
    List<String> findRolesByUserId(@Bind("userId") UUID userId);
}