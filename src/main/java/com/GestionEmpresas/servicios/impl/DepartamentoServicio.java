package com.GestionEmpresas.servicios.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.input.DepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoDto;
import com.GestionEmpresas.entity.Departamento;
import com.GestionEmpresas.entity.Empresa;
import com.GestionEmpresas.excepciones.idNotFoundException;
import com.GestionEmpresas.repository.DepartamentoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
import com.GestionEmpresas.servicios.IDepartamentoServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class DepartamentoServicio implements IDepartamentoServicio {

	@Autowired
	private DepartamentoRepository repoDptm;

	@Autowired
	private EmpresaRepository repoEmpresa;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public List<DepartamentoDto> findAll() {
		ArrayList<DepartamentoDto> listadepartamentos = new ArrayList<>();
		repoDptm.findAll().forEach(r -> listadepartamentos.add(convertToDto(r)));
		return listadepartamentos;
	}

	@Override
	public DepartamentoDto findById(Long id) {
		DepartamentoDto dptm = convertToDto(repoDptm.findById(id).orElseThrow(() -> new idNotFoundException(id)));
		return dptm;
	}

	@Override
	public DepartamentoDto addDepartamento(DepartamentoDtoInput eiD) {
		Departamento dptm = new Departamento();
		BeanUtils.copyProperties(eiD, dptm);
		Set<Empresa> empresas = new HashSet<>();
		
		eiD.getEmpresas().forEach(e -> empresas.add(repoEmpresa.findById(e.getId()).orElseThrow(() -> new idNotFoundException(eiD.getId()))));
		dptm.setEmpresas(empresas);
		DepartamentoDto dptmDto = convertToDto(repoDptm.save(dptm));
		return dptmDto;
	}

	private DepartamentoDto convertToDto(Departamento entity) {
		JMapper<DepartamentoDto, Departamento> mapper = new JMapper<>(DepartamentoDto.class, Departamento.class,
				jmapperApi);
		DepartamentoDto departamentoDto = mapper.getDestination(entity);
		return departamentoDto;
	}

}
