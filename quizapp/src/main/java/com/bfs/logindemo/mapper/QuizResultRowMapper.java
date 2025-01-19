package com.bfs.logindemo.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QuizResultRowMapper implements RowMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        result.put("quizId", rs.getInt("quiz_id"));
        result.put("timeStart", rs.getTimestamp("time_start"));
        result.put("timeEnd", rs.getTimestamp("time_end"));
        result.put("categoryName", rs.getString("category_name"));
        result.put("userFullName", rs.getString("firstName") + " " + rs.getString("lastName"));
        result.put("score", rs.getInt("score"));
        return result;
    }
}