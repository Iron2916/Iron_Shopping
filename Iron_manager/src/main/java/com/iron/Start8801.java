package com.iron;


import com.iron.properties.MinioProperties;
import com.iron.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@SpringBootApplication
public class Start8801 {

    public static void main(String[] args) {

        SpringApplication.run(Start8801.class, args);
    }
}
