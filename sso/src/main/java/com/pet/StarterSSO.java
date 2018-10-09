package com.pet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.pet.sso.mapper")
public class StarterSSO {
	public static void main(String[] args) {
		SpringApplication.run(StarterSSO.class, args);
	}

}
