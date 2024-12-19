package fr.plopanol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("fr.plopanol.*")
// Ajout du Envers Repository uniquement à des fin de consultations
@EnableJpaRepositories(value = "fr.plopanol.*", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class Application {
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}