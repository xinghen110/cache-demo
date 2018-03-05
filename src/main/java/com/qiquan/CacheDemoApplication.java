package com.qiquan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.qiquan.mapper")
@EnableCaching
@EnableScheduling
public class CacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheDemoApplication.class, args);
	}
}
