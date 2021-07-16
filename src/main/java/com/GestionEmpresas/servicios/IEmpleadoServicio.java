package com.GestionEmpresas.servicios;

import java.util.Optional;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;

public interface IEmpleadoServicio {
	public ListaGenericDto<EmpleadoDto> findAll(Optional<String> sortBy);
	public ListaGenericDto<EmpleadoDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy);
	public EmpleadoDto findById(Long id);
	public EmpleadoDto createOrUpdate(EmpleadoDtoInput eiD);
	public void deleteById(Long codEmpleado);
}
