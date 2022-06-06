package com.Ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class EcommerceApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(EcommerceApplication.class, args);
		File dir = new File(System.getProperty("user.dir")+"/images/products/");
		if(!dir.exists()){
			dir.mkdirs();
		}
	}

}
