package com.example.progetto;



import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Theme(variant = "light")

public class ProgettoApplication implements AppShellConfigurator{

	public static void main(String[] args) {
		SpringApplication.run(ProgettoApplication.class, args);
	}
	

}
