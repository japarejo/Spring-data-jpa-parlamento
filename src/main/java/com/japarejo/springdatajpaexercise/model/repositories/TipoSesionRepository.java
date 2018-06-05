package com.japarejo.springdatajpaexercise.model.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.japarejo.springdatajpaexercise.model.entities.TipoSesion;

@Repository
public interface TipoSesionRepository extends CrudRepository<TipoSesion, Long>{
	
}
