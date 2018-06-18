package com.japarejo.springdatajpaexercise.model.repositories.bd1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.japarejo.springdatajpaexercise.model.entities.Sala;

@Repository
public interface SalaRepository extends CrudRepository<Sala, Long> {

}
