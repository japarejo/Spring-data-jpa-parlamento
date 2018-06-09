package com.japarejo.springdatajpaexercise.model.repositories;

import com.japarejo.springdatajpaexercise.model.entities.Sesion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends CrudRepository<Sesion,Long> {

}
