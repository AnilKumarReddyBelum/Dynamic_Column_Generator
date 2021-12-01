package com.example.demo.service;

import com.example.model.ConfigModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfigModelRowMapper implements RowMapper<ConfigModel> {

    @Override
    public ConfigModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ConfigModel configModel = new ConfigModel();
        configModel.setColumnName(rs.getString("COLUMN_NAME"));
        configModel.setColumnType(rs.getString("DATA_TYPE"));
        configModel.setEntityName(rs.getString("TABLE_NAME"));
        return configModel;
    }
}
