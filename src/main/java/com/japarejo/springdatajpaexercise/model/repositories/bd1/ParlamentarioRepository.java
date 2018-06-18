package com.japarejo.springdatajpaexercise.model.repositories.bd1;

import com.japarejo.springdatajpaexercise.model.entities.Parlamentario;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParlamentarioRepository extends CrudRepository<Parlamentario,Long> {

	Parlamentario findByNombre(String nombre);

}
