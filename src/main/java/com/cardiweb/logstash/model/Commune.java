package com.cardiweb.logstash.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.cardiweb.logstash.data.CommuneCSV;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Commune {
	@Id
	private String insee;
	private String nom;
	private String wikipedia;
	private Double surface;
	private Float latitude;
	private Float longitude;
	private String statut;
	private Float xChefLieu;
	private Float yChefLieu;
	private Float altitude;
	private Float population;
	private Integer codeCanton;
	private String codeArrondissement;
	private String codeDepartement;
	private String nomDepartement;
	private String codeRegion;
	private String nomRegion;
	
	@OneToOne(mappedBy="commune")
	@JoinColumn(name="insee")
	private Maire maire;
	
	public Commune(CommuneCSV communeCsv) {
		this.insee = communeCsv.getInsee();
		this.nom = communeCsv.getNom();
		this.wikipedia = communeCsv.getWikipedia();
		this.surface = communeCsv.getSurf_m2();
		this.latitude = communeCsv.getLat_centro();
		this.longitude = communeCsv.getLon_centro();
		this.statut = communeCsv.getStatut();
		this.xChefLieu = communeCsv.getX_chf_lieu();
		this.yChefLieu = communeCsv.getY_chf_lieu();
		this.altitude = communeCsv.getZ_moyen();
		this.population = communeCsv.getPopulation();
		this.codeCanton = communeCsv.getCode_cant();
		this.codeArrondissement = communeCsv.getCode_arr();
		this.codeDepartement = communeCsv.getCode_dept();
		this.nomDepartement = communeCsv.getNom_dept();
		this.codeRegion = communeCsv.getCode_reg();
		this.nomRegion = communeCsv.getNom_region();
	}
}
