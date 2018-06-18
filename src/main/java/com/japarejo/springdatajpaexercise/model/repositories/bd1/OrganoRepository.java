package com.japarejo.springdatajpaexercise.model.repositories.bd1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.japarejo.springdatajpaexercise.model.entities.Organo;

@Repository
public interface OrganoRepository extends CrudRepository<Organo, Long> {

	Organo findByAbreaviatura(String string);

}
