package com.zzdz;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

@EnableRabbit
@EnableCaching
@SpringBootApplication
public class SpringbootMybatisDruidApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMybatisDruidApplication.class, args);
    }

}
