package com.nbxapp.creepersvr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CreeperSvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreeperSvrApplication.class, args);
	}
}
