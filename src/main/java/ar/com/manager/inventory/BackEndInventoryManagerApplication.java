package ar.com.manager.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BackEndInventoryManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndInventoryManagerApplication.class, args);
	}

}

