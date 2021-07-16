package com.GestionEmpresas.servicios;

import java.util.List;

import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoCargoDto;
import com.GestionEmpresas.dto.response.EmpleadoDepartamentoDto;

public interface IEmpleadoDepartamentoServicio {
	public List<EmpleadoDepartamentoDto> findAll();
	public EmpleadoDepartamentoDto findById(Long id);
	public List<EmpleadoDepartamentoDto> findByEmpleadoId(Long codEmpleado);
	public List<DepartamentoCargoDto> findDepartamentoCargoByEmpleadoId(Long codEmpleado);
	public EmpleadoDepartamentoDto createOrUpdate(EmpleadoDepartamentoDtoInput eiD);
	void deleteById(Long id);
}
