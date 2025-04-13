package com.taskapproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TaskApprovalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApprovalApplication.class, args);
	}

}
