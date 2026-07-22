package net.hackyourfuture.tickettrackingsystem.users.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import net.hackyourfuture.tickettrackingsystem.users.model.UserModel;

@RegisterBeanMapper(UserModel.class)
public interface UserDao {

    @SqlQuery("""
                SELECT *
                FROM users
                WHERE user_id = :id
            """)
    UserModel findById(@Bind("id") UUID id);

    @SqlQuery("""
            SELECT *
            FROM users
                """)
    List<UserModel> findAllUsers();

    @SqlQuery("""
                INSERT INTO users (name, email)
                VALUES (:name, :email)
                RETURNING user_id, name, email
            """)
    UserModel createUser(
            @Bind("name") String name,
            @Bind("email") String email);

    @SqlQuery("""
                UPDATE users
                SET name = :name, email = :email
                WHERE user_id = :id
                RETURNING user_id, name, email
            """)
    UserModel updateUser(
            @Bind("id") UUID id,
            @Bind("name") String name,
            @Bind("email") String email);

    @SqlUpdate("""
                DELETE FROM users
                WHERE user_id = :id
            """)
    boolean deleteUser(@Bind("id") UUID id);
}