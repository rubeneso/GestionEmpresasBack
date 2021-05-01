package com.GestionEmpresas.servicios;

import java.util.List;

import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;

public interface IEmpresaServicio {
	public List<EmpresaDto> findAll();
	public EmpresaDto findById(Long id) ;
	public EmpresaDto addEmpresa(EmpresaDtoInput eiD);
	public List<EmpresaDto> findAllPaginated(Integer pageNo, Integer pageSize, String sortBy);
	public List<EmpresaDto> findAllPaginated(Integer pageNo, Integer pageSize);
}
