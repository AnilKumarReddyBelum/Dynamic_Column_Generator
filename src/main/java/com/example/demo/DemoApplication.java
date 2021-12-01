package com.example.demo;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class DemoApplication {

    private ProductService productService;

    public DemoApplication(ProductService productService) {
        this.productService = productService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(ProductService productService) {
        log.info("demoData inserting for testing puprose");
        return args -> {
            Product product = null;
            for (int i = 0; i < 10; i++) {
                product = new Product();
                product.setName("Product Number " + i);
                productService.create(product);
            }
        };
    }

}
