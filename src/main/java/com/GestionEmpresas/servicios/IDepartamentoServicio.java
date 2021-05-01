package com.GestionEmpresas.servicios;

import java.util.List;

import com.GestionEmpresas.dto.input.DepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoDto;

public interface IDepartamentoServicio {
	public List<DepartamentoDto> findAll();
	public DepartamentoDto findById(Long id);
	public DepartamentoDto addDepartamento(DepartamentoDtoInput eiD);
}
