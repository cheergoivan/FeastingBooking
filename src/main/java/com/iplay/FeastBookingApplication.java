package com.iplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;

@SpringBootApplication
@EnableCaching
@ServletComponentScan
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class FeastBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeastBookingApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public IdGenerator getIdGenerator() {
        return new CommonSelfIdGenerator();
    }
	
}
