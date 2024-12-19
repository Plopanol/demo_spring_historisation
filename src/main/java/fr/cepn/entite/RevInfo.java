package fr.cepn.entite;

import jakarta.persistence.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import java.time.LocalDateTime;

/**
 * Création de la table RevInfo à la main pour eviter les soucis de Date / Identifiant
 */
@Entity
@RevisionEntity
@Table(name = "revinfo")
public class RevInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@RevisionNumber
	@Column(name = "rev")
	private long rev;

	@RevisionTimestamp
	@Column(name = "revtstmp")
	private LocalDateTime revtstmp;

	public long getRev() {
		return rev;
	}

	public void setRev(long rev) {
		this.rev = rev;
	}

	public LocalDateTime getRevtstmp() {
		return revtstmp;
	}

	public void setRevtstmp(LocalDateTime revtstmp) {
		this.revtstmp = revtstmp;
	}

	public RevInfo(long rev, LocalDateTime revtstmp) {
		this.rev = rev;
		this.revtstmp = revtstmp;
	}

	public RevInfo(){

	}

}
