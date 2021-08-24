package com.upvote.aismpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AismproApplication {

	public static void main(String[] args) {
		SpringApplication.run(AismproApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(Application.class);
	}

}
