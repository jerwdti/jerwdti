package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.User;
import com.bfs.logindemo.mapper.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    // Constructor injection
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Create a new user
    public void createNewUser(User user) {
        String sql = "INSERT INTO user (firstName, lastName, email, password, is_active, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.isAdmin());
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        String query = "SELECT * FROM user";
        return jdbcTemplate.query(query, new UserRowMapper());
    }

    public Optional<User> getUserByEmail(String email) {
        try {
            String sql = "SELECT * FROM user WHERE email = ?";
            return jdbcTemplate.query(sql, new UserRowMapper(), email).stream().findAny();
        } catch (Exception e) {
            System.err.println("Error fetching user by email: " + e.getMessage());
            return Optional.empty();
        }
    }

    public List<User> getUsersWithPagination(int offset, int limit) {
        String sql = "SELECT id, firstName, lastName, email, is_active " +
                "FROM user LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new Object[]{limit, offset}, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setActive(rs.getBoolean("is_active"));
            return user;
        });
    }

    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM user";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void toggleUserStatus(int userId) {
        String sql = "UPDATE user SET is_active = NOT is_active WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
}