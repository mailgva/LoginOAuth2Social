package com.gorbatenko.loginoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class Loginoauth2Application  {

	public static void main(String[] args) {
		SpringApplication.run(Loginoauth2Application.class, args);
	}

}
