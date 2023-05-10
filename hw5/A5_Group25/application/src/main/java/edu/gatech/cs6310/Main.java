package edu.gatech.cs6310;

import edu.gatech.cs6310.dao.DeliveryServiceDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(DeliveryServiceDAO deliveryServiceDAO) {
		return runner -> {
			System.out.println("Welcome to the Grocery Express Delivery Service!\n" +
					"\nTo view a list of available commands, please enter 'available_commands'.\n" +
					"\nPlease enter a command:");
			DeliveryService simulator = new DeliveryService();
			simulator.commandLoop(deliveryServiceDAO);

			System.exit(0);
		};
	}
}
