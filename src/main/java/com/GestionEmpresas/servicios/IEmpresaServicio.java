package com.GestionEmpresas.servicios;

import java.util.Optional;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;

public interface IEmpresaServicio {
	public ListaGenericDto<EmpresaDto> findAll(Optional<String> sortBy);
	public ListaGenericDto<EmpresaDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy);
	public EmpresaDto findById(Long id) ;
	public EmpresaDto addEmpresa(EmpresaDtoInput eiD);
}
