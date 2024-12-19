package fr.cepn.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableEnversRepositories
@EnableJpaAuditing
@Configuration
public class HistorisationConfiguration {
	// Activation de la gestion des champs date/user si necessaire
}