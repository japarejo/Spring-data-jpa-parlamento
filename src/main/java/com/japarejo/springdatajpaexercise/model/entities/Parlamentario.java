package com.japarejo.springdatajpaexercise.model.entities;

import java.util.List;

public class Parlamentario {
	private long id;
	private String nombre;
	
	private List<Organo> organos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Organo> getOrganos() {
		return organos;
	}

	public void setOrganos(List<Organo> organos) {
		this.organos = organos;
	}
	
	
}
