package com.GestionEmpresas.servicios.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;
import com.GestionEmpresas.entity.Departamento;
import com.GestionEmpresas.entity.Empresa;
import com.GestionEmpresas.excepciones.NullOrEmptyValueException;
import com.GestionEmpresas.excepciones.WrongArgsException;
import com.GestionEmpresas.excepciones.idNotFoundException;
import com.GestionEmpresas.repository.DepartamentoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
import com.GestionEmpresas.servicios.IEmpresaServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class EmpresaServicio implements IEmpresaServicio{

	@Autowired
	private EmpresaRepository repo;

	@Autowired
	private DepartamentoRepository repoDptm;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public List<EmpresaDto> findAll() {
		ArrayList<EmpresaDto> listaempresas = new ArrayList<>();
		repo.findAll().forEach(r -> listaempresas.add(convertToDto(r)));
		return listaempresas;
	}

	@Override
	public List<EmpresaDto> findAllPaginated(Integer pageNo, Integer pageSize, String sortBy) {
		if (pageSize < 1 || pageNo < 0 || sortBy == "") {
			throw new WrongArgsException();
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		ArrayList<EmpresaDto> listaempresas = new ArrayList<>();

		repo.findAll(pageable).forEach(r -> listaempresas.add(convertToDto(r)));
		return listaempresas;
	}

	@Override
	public List<EmpresaDto> findAllPaginated(Integer pageNo, Integer pageSize) {
		if (pageSize < 1 || pageNo < 0) {
			throw new WrongArgsException();
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		ArrayList<EmpresaDto> listaempresas = new ArrayList<>();

		repo.findAll(pageable).forEach(r -> listaempresas.add(convertToDto(r)));

		return listaempresas;
	}

	@Override
	public EmpresaDto findById(Long id) {
		EmpresaDto empresaDto = convertToDto(repo.findById(id).orElseThrow(() -> new idNotFoundException(id)));
		return empresaDto;
	}

	@Override
	public EmpresaDto addEmpresa(EmpresaDtoInput eiD) {
		if(eiD.getNombre() == "" || eiD.getNombre() == null) throw new NullOrEmptyValueException("nombre");
		if(eiD.getPotenciaContratada() == null) eiD.setPotenciaContratada(0d);
		
		Empresa empresa = new Empresa();
		BeanUtils.copyProperties(eiD, empresa);

		Set<Departamento> departamentos = new HashSet<>();
		EmpresaDto empresaDto = new EmpresaDto();

		eiD.getDepartamentos().forEach(d -> departamentos
				.add(repoDptm.findById(d.getId()).orElseThrow(() -> new idNotFoundException(eiD.getId()))));
		empresa.setDepartamentos(departamentos);
		empresaDto = convertToDto(repo.save(empresa));

		return empresaDto;
	}

	public EmpresaDto convertToDto(Empresa entity) {
		JMapper<EmpresaDto, Empresa> mapper = new JMapper<>(EmpresaDto.class, Empresa.class, jmapperApi);
		EmpresaDto empresaDto = mapper.getDestination(entity);
		return empresaDto;
	}

}
