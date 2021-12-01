package com.example.demo.service;

import com.example.model.Payload;

public interface DynamicProductService {

    String insert(Payload payload);

    String update(Payload payload);
}
