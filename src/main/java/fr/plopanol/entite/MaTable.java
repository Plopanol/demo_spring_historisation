package fr.plopanol.entite;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Audited(withModifiedFlag = true)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ma_table")
public class MaTable {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@Column(name = "email")
	private String email;

	@CreatedDate
	@Column(name = "date_cre")
	private LocalDateTime dateCre;
	@LastModifiedDate
	@Column(name = "date_maj")
	private LocalDateTime dateMaj;

	public MaTable(UUID id, String nom, String prenom, String email, LocalDateTime dateCre, LocalDateTime dateMaj) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.dateCre = dateCre;
		this.dateMaj = dateMaj;
	}

	public MaTable(){

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getDateCre() {
		return dateCre;
	}

	public void setDateCre(LocalDateTime dateCre) {
		this.dateCre = dateCre;
	}

	public LocalDateTime getDateMaj() {
		return dateMaj;
	}

	public void setDateMaj(LocalDateTime dateMaj) {
		this.dateMaj = dateMaj;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}