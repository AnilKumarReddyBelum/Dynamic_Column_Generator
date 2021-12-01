package com.example.demo.service;

import com.example.model.Payload;

public interface DynamicProductService {

    Object insert(Payload payload);

    Object update(Payload payload);
}
