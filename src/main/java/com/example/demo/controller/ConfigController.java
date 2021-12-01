package com.example.demo.controller;

import com.example.demo.service.ConfigService;
import com.example.demo.service.DynamicProductService;
import com.example.model.ConfigModel;
import com.example.model.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("config")
public class ConfigController {

    private ConfigService configService;
    private DynamicProductService dynamicProductService;

    public ConfigController(ConfigService configService, DynamicProductService dynamicProductService) {
        this.configService = configService;
        this.dynamicProductService = dynamicProductService;
    }

    @PostMapping
    public void addColumn(@RequestBody ConfigModel configModel) throws SQLException {
        configService.addColumn(configModel);
    }

    @PostMapping("insert")
    public String insertData(@RequestBody Payload payload) {
        return dynamicProductService.insert(payload);
    }

    @PostMapping("update")
    public String updateData(@RequestBody Payload payload) {
        return dynamicProductService.update(payload);
    }

}
