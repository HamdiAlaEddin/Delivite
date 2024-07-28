package tn.solixy.delivite;

import jakarta.transaction.Transactional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Transactional
@SpringBootApplication
public class DeliviteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliviteApplication.class, args);
	}

}
