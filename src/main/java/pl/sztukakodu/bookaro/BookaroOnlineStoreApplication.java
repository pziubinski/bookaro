package pl.sztukakodu.bookaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookaroOnlineStoreApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(BookaroOnlineStoreApplication.class, args);
		System.out.println(context);
	}

}