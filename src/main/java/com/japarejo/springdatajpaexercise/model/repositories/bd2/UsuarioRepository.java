package com.japarejo.springdatajpaexercise.model.repositories.bd2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.japarejo.springdatajpaexercise.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
