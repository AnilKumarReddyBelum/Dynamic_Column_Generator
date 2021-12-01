package com.example.model;

import lombok.Data;

@Data
public class Payload {

    private String tableName;
    private String schemaName;
    private Object entityData;

}
