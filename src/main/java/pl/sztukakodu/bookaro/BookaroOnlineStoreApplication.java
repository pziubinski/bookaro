package pl.sztukakodu.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.sztukakodu.bookaro.catalog.application.CatalogController;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.util.List;

@SpringBootApplication
public class BookaroOnlineStoreApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(BookaroOnlineStoreApplication.class, args);
		System.out.println(context);
	}

}