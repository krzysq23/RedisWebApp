package com.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.redis.config", "com.redis.models",
		"com.redis.service", "com.redis.queue", "com.redis.controller" })
public class RedisWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisWebAppApplication.class, args);
	}

}
