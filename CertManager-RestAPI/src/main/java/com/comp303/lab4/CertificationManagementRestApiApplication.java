package com.comp303.lab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient 
public class CertificationManagementRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertificationManagementRestApiApplication.class, args);
		System.out.println("CertificationManagementRestAPI - Andrea/Jiwoong");
	}
}

//@SpringBootApplication
//public class CertificationManagementRestApiApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(CertificationManagementRestApiApplication.class, args);
//		System.out.println("CertificationManagementRestAPI - Andrea/Jiwoong");
//	}
//
//}
