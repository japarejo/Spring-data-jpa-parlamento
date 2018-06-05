package com.japarejo.springdatajpaexercise.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ORGANO database table.
 * 
 */
@Entity
@Table(name="ORGANO")
public class Organo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false, precision=10)
	private long id;

	@Column(nullable=false, length=100)
	private String abreaviatura;

	@Column(nullable=false, length=100)
	private String descripcion;

	@Column(precision=10)
	private Long orden;

	public Organo() {
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