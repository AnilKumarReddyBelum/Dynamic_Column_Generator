package com.example.demo.service;

import com.example.model.ConfigModel;

import java.sql.SQLException;

public interface ConfigService {

    public void addColumn(ConfigModel configModel) throws SQLException;
}
