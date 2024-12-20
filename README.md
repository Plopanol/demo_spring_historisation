# Contexte 

Projet de demo pour l'historisation sous Spring Boot .

2 Framework distinct.

- Hibernate Envers pour l'historisation de la donnée en elle même
- Spring Audit pour la tracabilité (Date/User)


# Execution

BDD Embarqué H2, la BDD sera dans un fichier à la racine du projet
Voir configuration d'accès dans `application.yml`

Commande maven : `clean -U spring-boot:run`

Url d'accès : http://localhost:8080/v1/

Rest disponible :
- @Get v1 -> All
- @Get v1/{id} -> ById
- @Post
    Exemple de body attendu pour le Post :
  `{
      "nom": "Giblert 1er",
      "prenom": "Hector",
      "email": "email_3534@gmail.com"
  }`

### Info Utile 

Les dates sont ajoutées dans l'entité uniquement au moment du flush.
Donc lors d'un save, si on renvoi l'entite sans un flush, les dates ne seront pas renseignées.

### Mise en place

La configuration est faite à partir du fichier `HistorisationConfiguration.java`
```java
@EnableEnversRepositories    
@EnableJpaAuditing    
@Configuration    public class HistorisationConfiguration {    } 
```
Toutes les entités (Sauf Revinfo) devront être annotées :

```java
@Audited    
@EntityListeners(AuditingEntityListener.class)
```

Possibilité d'utiliser une table abstraite `AbstractEntite.java` qui regroupent les champs de révision ci-dessous, ce qui permet  
de connaitre qui à fait quoi et quand.

```java
@CreatedDate    
@Column(name = "date_cre")    private LocalDateTime dateCre;   
@LastModifiedDate    
@Column(name = "date_maj")    private LocalDateTime dateMaj;    
@Column(name = "date_suppr")    private LocalDateTime dateSuppr;  
@CreatedBy    
@ManyToOne(fetch = FetchType.LAZY, optional = false)    
@JoinColumn(name = "fk_user_cre", nullable = false)    
private Utilisateur fkUserCre;    @LastModifiedBy    
@ManyToOne(fetch = FetchType.LAZY, optional = false)    
@JoinColumn(name = "fk_user_maj", nullable = false)    
private Utilisateur fkUserMaj;
```

L'utilisateur est récuperé directement dans le contexteSpring par la classe `HistorisationUser.java` (Actuellement pas commité)

```java
@Component    
public class HistorisationUser implements AuditorAware<Utilisateur> {           
	private static final Logger logger = LoggerFactory.getLogger(  
			UtilisateurParDefautFilter.class.getName());            
	@Override  
	public Optional<Utilisateur> getCurrentAuditor() {            
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();           
			logger.debug("Utilisateur récupérer pour l'historisation : " + authentication);
			Utilisateur user = null;            // TODO GERER LES UTILISATEURS                        if (authentication == null || !authentication.isAuthenticated()) {     
				return null;            
			} else {
				 UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authentication.getDetails();
				 user = userDetailsCustom.getUtilisateur();            
			}
			 return Optional.ofNullable(user);  
		}    
	}  
```

Création de la table `revinfo` manuellement afin d'éviter les erreurs d'index et de type pour la table revinfo par  
défaut.  
Erreur `syntaxe en entrée invalide pour le type bigint` pour le champ revtstmp ou `revinfo_seq doesn't exist`

```java
@Entity    
@RevisionEntity    
@Table(name = "revinfo")    
@Getter    
@Setter    
@AllArgsConstructor    
@NoArgsConstructor    
public class RevInfo {            
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)        
	@RevisionNumber        
	@Column(name = "rev")        
	private long rev;            
	@RevisionTimestamp  
	@Column(name = "revtstmp")        
	private LocalDateTime revtstmp;    
}
```

Script de création de la table `revinfo` pour info.

```sql
CREATE SEQUENCE IF NOT EXISTS revinfo_rev_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;  
CREATE TABLE revinfo  
(    rev      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
	revtstmp timestamp(6),    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)   
 );
```
Une fois configuré il faut créer les tables d'historisation, pour cela nous allons utiliser spring.  
Modifier le paramètre suivant dans `application.properties` afin de créer automatiquement les tables manquantes.

```properties
spring.jpa.hibernate.ddl-auto=update
```
Lancer le projet, vérifier que les tables soient bien créés, toutes les tables existantes sont doublées avec un suffixe  `_aud`.

Désactiver la ligne d'update de la bdd précédemment ajoutée.

Une fois les tables créés, générer le script de migration avec Flyway (Voir Chapitre Flyway)

Tester le en recréant votre bdd avec le dump de reprise + initialisation de la bdd avec flyway.

### Utilisation

Elle est automatique, il faut ajouter `entityManager.flush` juste après le repo.save afin de déclencher le listener qui  
mettra a jour la date maj.

## Configuration Spring

```properties
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```
