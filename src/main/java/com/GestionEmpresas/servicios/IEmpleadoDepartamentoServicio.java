package com.GestionEmpresas.servicios;

import java.util.List;

import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDepartamentoDto;

public interface IEmpleadoDepartamentoServicio {
	public List<EmpleadoDepartamentoDto> findAll();
	public EmpleadoDepartamentoDto findById(Long id);
	public EmpleadoDepartamentoDto addEmpleadoDepartamento(EmpleadoDepartamentoDtoInput eiD);
	void deleteById(Long id);
}
