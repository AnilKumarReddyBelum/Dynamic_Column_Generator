package com.example.demo.service;

import com.example.model.ConfigModel;
import com.example.model.Payload;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DynamicProductServiceImpl implements DynamicProductService {

    public static final String META_DATA_QUERY = "SELECT table_name, column_name, data_type " +
            "FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";


    private JdbcTemplate jdbcTemplate;

    public DynamicProductServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String insert(Payload payload) {
        String message = null;
        List<ConfigModel> configModelList = jdbcTemplate.query(META_DATA_QUERY, new Object[]{payload.getSchemaName(), payload.getTableName()}, new ConfigModelRowMapper());
        if (configModelList != null && payload != null && payload.getEntityData() != null) {
            List<String> columns = configModelList.stream().map(cm -> cm.getColumnName()).collect(Collectors.toList());
            String insertQuery = prepareInsertQuery(columns, payload.getTableName(), payload.getSchemaName());
            log.info("Insert Query -> {}", insertQuery);
            ObjectMapper objectMapper = new ObjectMapper();
            String json;
            try {
                json = objectMapper.writeValueAsString(payload.getEntityData());
                log.info("Json preparation completed {} ", json);
                Map<String, Object> map
                        = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
                });
                // Note: ID will populate automatically, it is sequence in database level
                map.remove("id");
                for (String c : columns) {
                    if (!map.containsKey(c) && !c.equalsIgnoreCase("id")) {
                        map.put(c, "");
                    }
                }
                int rows = jdbcTemplate.update(insertQuery, map.values().stream().collect(Collectors.toList()).toArray());
                if (rows > 0) {
                    message = "Data saved into database successfully!";
                } else {
                    message = "Some thing went wrong!";
                }
            } catch (Exception exception) {
                log.error("exception while converting into a json string --> {} ", exception.getCause());
            }

        }
        return message;
    }


    private static String prepareInsertQuery(List<String> columnList, String tableName, String schemaName) {
        List<String> columns = new ArrayList<>(columnList);
        // Note: ID will populate automatically, it is sequence in database level
        columns.remove("id");
        StringBuilder paramsBuilder = new StringBuilder(" (");
        for (int i = 0; i < columns.size(); i++) {
            if (i < columns.size() - 1) {
                paramsBuilder.append("?, ");
            } else {
                paramsBuilder.append("?");
            }
        }
        paramsBuilder.append(" )");
        StringBuilder insertQueryBuilder = new StringBuilder("INSERT INTO ").append(schemaName).append(".").append(tableName).append(" (")
                .append(columns.stream().collect(Collectors.joining(","))).append(")")
                .append(" VALUES ").append(paramsBuilder);
        return insertQueryBuilder.toString();
    }

    @Override
    public String update(Payload payload) {
        String message = null;
        if (payload != null) {
            List<ConfigModel> configModelList = jdbcTemplate.query(META_DATA_QUERY, new Object[]{payload.getSchemaName(), payload.getTableName()}, new ConfigModelRowMapper());
            if (configModelList != null && payload != null && payload.getEntityData() != null) {
                List<String> columns = configModelList.stream().map(cm -> cm.getColumnName()).collect(Collectors.toList());
                String updateQuery = prepareUpdateQuery(columns, payload.getTableName(), payload.getSchemaName());
                log.info("Update Query -> {}", updateQuery);
                ObjectMapper objectMapper = new ObjectMapper();
                String json;
                try {
                    json = objectMapper.writeValueAsString(payload.getEntityData());
                    log.info("Json preparation completed {} ", json);
                    Map<String, Object> map
                            = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
                    });

                    for (String c : columns) {
                        if (!map.containsKey(c) && !c.equalsIgnoreCase("id")) {
                            map.put(c, "");
                        }
                    }

                    List<Object> values = map.values().stream().collect(Collectors.toList());
                    values.add(map.get("id"));

                    int rows = jdbcTemplate.update(updateQuery, values.toArray());
                    if (rows > 0) {
                        message = "Data saved into database successfully!";
                    } else {
                        message = "Some thing went wrong!";
                    }
                } catch (Exception exception) {
                    log.error("exception while converting into a json string --> {} ", exception.getCause());
                }


            }
            return message;
        } else {
            return message;
        }
    }

    private static String prepareUpdateQuery(List<String> columnList, String tableName, String schemaName) {
        List<String> columns = new ArrayList<>(columnList);
        StringBuilder updateQueryBuilder = new StringBuilder("UPDATE ").append(schemaName).append(".").append(tableName)
                .append(" SET ");
        int columnsCount = 0;
        for (String c : columns) {
            if (columnsCount < columns.size() - 1) {
                updateQueryBuilder.append(c).append(" = ?, ");
            } else {
                updateQueryBuilder.append(c).append(" = ? ");
            }
            columnsCount++;
        }
        updateQueryBuilder.append(" WHERE ").append(columns.get(0)).append(" = ? ");
        return updateQueryBuilder.toString();
    }
}
