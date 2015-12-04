package com.cardiweb.logstash.data;

import lombok.Data;

@Data
public class CommuneCSV {
	private String insee;
	private String nom;
	private String wikipedia;
	private Double surf_m2;
	private Float lat_centro;
	private Float lon_centro;
	private String statut;
	private Float x_chf_lieu;
	private Float y_chf_lieu;
	private Float z_moyen;
	private Float population;
	private Integer code_cant;
	private String code_arr;
	private String code_dept;
	private String nom_dept;
	private String code_reg;
	private String nom_region;
}
