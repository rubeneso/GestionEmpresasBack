package com.GestionEmpresas.servicios;

import java.util.Optional;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.DepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoDto;

public interface IDepartamentoServicio {
	public ListaGenericDto<DepartamentoDto> findAll(Optional<String> sortBy);
	public ListaGenericDto<DepartamentoDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy);
	public DepartamentoDto findById(Long id);
	public DepartamentoDto createOrUpdate(DepartamentoDtoInput eiD);
}
