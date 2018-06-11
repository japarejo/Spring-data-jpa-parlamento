package com.japarejo.springdatajpaexercise.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the ORGANO database table.
 * 
 */

public class Organo implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;


	private String abreaviatura;


	private String descripcion;


	private Long orden;

	@Transient
	private List<Parlamentario> miembros;
	
	public Organo() {
		miembros=new ArrayList<Parlamentario>();
	}

	public List<Parlamentario> getMiembros() {
		return miembros;
	}

	public void setMiembros(List<Parlamentario> miembros) {
		this.miembros = miembros;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbreaviatura() {
		return this.abreaviatura;
	}

	public void setAbreaviatura(String abreaviatura) {
		this.abreaviatura = abreaviatura;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getOrden() {
		return this.orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

}