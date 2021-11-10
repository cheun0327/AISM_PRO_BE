package com.upvote.aismpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy // Enable AOP
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class AismproApplication {

	public static void main(String[] args) {
		SpringApplication.run(AismproApplication.class, args);

		System.out.println("**spring start**");
	}

}

