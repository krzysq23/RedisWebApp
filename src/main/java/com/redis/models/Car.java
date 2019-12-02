package com.redis.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String model;
    private String brand;

}
