package com.bfs.logindemo.dao;

import com.bfs.logindemo.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDao {

    private final JdbcTemplate jdbcTemplate;

    public CategoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll() {
        String sql = "SELECT id, name FROM category";
        return jdbcTemplate.query(sql, categoryRowMapper());
    }

    public Category findById(int id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, categoryRowMapper());
    }

    private RowMapper<Category> categoryRowMapper() {
        return (rs, rowNum) -> new Category(
                rs.getInt("id"),
                rs.getString("name")
        );
    }
}