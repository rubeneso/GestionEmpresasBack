package com.GestionEmpresas.servicios;

import java.util.List;

import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;

public interface IEmpleadoServicio {
	public List<EmpleadoDto> findAll();
	public EmpleadoDto findById(Long id);
	public EmpleadoDto addEmpleado(EmpleadoDtoInput eiD);
	public List<EmpleadoDto> findAllPaginated(Integer pageNo, Integer pageSize, String sortBy);
	public List<EmpleadoDto> findAllPaginated(Integer pageNo, Integer pageSize);
}
