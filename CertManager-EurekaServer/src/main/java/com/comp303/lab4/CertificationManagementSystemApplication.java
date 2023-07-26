package com.comp303.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CertificationManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificationManagementSystemApplication.class, args);
		System.out.println("COMP303_Assignment4_Andrea_Jiwoong");
	}
}
