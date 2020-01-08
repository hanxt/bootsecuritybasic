package com.zimug.jwtserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = {"com.zimug.jwtserver"})
public class JWTServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(JWTServerApplication.class, args);
    }

}
