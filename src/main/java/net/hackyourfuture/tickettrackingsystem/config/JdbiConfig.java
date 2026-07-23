package net.hackyourfuture.tickettrackingsystem.config;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.hackyourfuture.tickettrackingsystem.projects.dao.ProjectDao;
import net.hackyourfuture.tickettrackingsystem.users.dao.UserDao;

@Configuration
public class JdbiConfig {

    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        Jdbi jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    @Bean
    public UserDao userDao(Jdbi jdbi) {
        return jdbi.onDemand(UserDao.class);
    }

    @Bean
    public ProjectDao projectDao(Jdbi jdbi) {
        return jdbi.onDemand(ProjectDao.class);
    }
}