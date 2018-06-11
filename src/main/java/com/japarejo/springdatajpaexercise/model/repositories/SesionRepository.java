package com.japarejo.springdatajpaexercise.model.repositories;

import com.japarejo.springdatajpaexercise.model.entities.Organo;
import com.japarejo.springdatajpaexercise.model.entities.Sala;
import com.japarejo.springdatajpaexercise.model.entities.Sesion;
import com.japarejo.springdatajpaexercise.model.entities.TipoSesion;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends CrudRepository<Sesion,Long> {
		
}
