package com.whizzarc.inventoryservice;

import com.whizzarc.inventoryservice.model.Inventory;
import com.whizzarc.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//		return args -> {
//			Inventory inventory = new Inventory();
//			inventory.setSkuCode("Test1");
//			inventory.setQuantity(100.0);
//
//			Inventory inventory1 = new Inventory();
//			inventory1.setSkuCode("Test2");
//			inventory1.setQuantity((double) 0);
//
//			List<Inventory> inventories = new ArrayList<>();
//			inventories.add(inventory);
//			inventories.add(inventory1);
//			inventoryRepository.saveAll(inventories);
//		};
//	}

}
