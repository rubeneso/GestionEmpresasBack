package com.GestionEmpresas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.GestionEmpresas.entity.EmpleadoDepartamento;

@Repository
public interface EmpleadoDepartamentoRepository extends JpaRepository<EmpleadoDepartamento, Long>{
	
}
