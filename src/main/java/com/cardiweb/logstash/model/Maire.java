package com.cardiweb.logstash.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cardiweb.logstash.data.MaireCSV;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Maire {

	@Id
	@GeneratedValue
	private Long id;

	private String nom;
	private String prenom;
	private String civilite;
	private LocalDate dateNaissance;

	@OneToOne
	@JoinColumn(name="insee")
	private Commune commune;

	@OneToOne
	@JoinColumn(name="code")
	private Profession profession;

	public Maire(MaireCSV maireCSV, Profession profession, Commune commune) {
		this.nom= maireCSV.getNompsn();
		this.prenom = maireCSV.getPrepsn();
		this.dateNaissance= LocalDate.parse(maireCSV.getNaissance(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.civilite=maireCSV.getCivpsn();
		this.commune = commune;
		this.profession=profession;
	}
}
