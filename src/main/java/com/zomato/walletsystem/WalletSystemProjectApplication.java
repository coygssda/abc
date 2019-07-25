package com.zomato.walletsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.zomato.walletsystem")
public class WalletSystemProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletSystemProjectApplication.class, args);
	}

}
