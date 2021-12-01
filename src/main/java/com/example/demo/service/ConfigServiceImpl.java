package com.example.demo.service;

import com.example.model.ConfigModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addColumn(ConfigModel configModel) throws SQLException {
        log.info("Enter into addColumn");
        final String alterQuery = new StringBuilder("ALTER TABLE public.")
                .append(configModel.getEntityName()).append(" ADD COLUMN ")
                .append(configModel.getColumnName()).append(" ")
                .append(configModel.getColumnType()).toString();
        log.info("Enter into addColumn, query is {} ", alterQuery);

        jdbcTemplate.execute(alterQuery);

    }

}
