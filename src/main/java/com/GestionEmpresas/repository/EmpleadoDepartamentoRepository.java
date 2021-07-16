package com.GestionEmpresas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.GestionEmpresas.entity.EmpleadoDepartamento;

@Repository
public interface EmpleadoDepartamentoRepository extends JpaRepository<EmpleadoDepartamento, Long>{
	List<EmpleadoDepartamento> findByEmpleadoId(Long codEmpleado);
	EmpleadoDepartamento findOneByEmpleadoIdAndDepartamentoId(Long codEmpleado, Long codDepartamento);
	
	@Modifying
	@Query("delete from EmpleadoDepartamento t where t.id = ?1")
	void deleteById(Long codEmpleadoDepartamento);
}
