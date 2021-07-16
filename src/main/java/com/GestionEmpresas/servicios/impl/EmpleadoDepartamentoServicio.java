package com.GestionEmpresas.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoCargoDto;
import com.GestionEmpresas.dto.response.EmpleadoDepartamentoDto;
import com.GestionEmpresas.entity.EmpleadoDepartamento;
import com.GestionEmpresas.excepciones.idNotFoundException;
import com.GestionEmpresas.repository.DepartamentoRepository;
import com.GestionEmpresas.repository.EmpleadoDepartamentoRepository;
import com.GestionEmpresas.repository.EmpleadoRepository;
import com.GestionEmpresas.servicios.IEmpleadoDepartamentoServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class EmpleadoDepartamentoServicio implements IEmpleadoDepartamentoServicio {

	@Autowired
	EmpleadoDepartamentoRepository repo;

	@Autowired
	EmpleadoRepository repoEmp;

	@Autowired
	DepartamentoRepository repoDep;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public List<EmpleadoDepartamentoDto> findAll() {
		ArrayList<EmpleadoDepartamentoDto> listaEmpleadoDepartamento = new ArrayList<>();
		repo.findAll().forEach(r -> listaEmpleadoDepartamento.add(convertToDto(r)));
		return listaEmpleadoDepartamento;
	}

	@Override
	public EmpleadoDepartamentoDto findById(Long id) {
		EmpleadoDepartamentoDto resultado = new EmpleadoDepartamentoDto();

		resultado = convertToDto(repo.findById(id).get());
		return resultado;
	}
	
	@Override
	public List<EmpleadoDepartamentoDto> findByEmpleadoId(Long codEmpleado) {
		List<EmpleadoDepartamentoDto> resultado;
		
		resultado = repo.findByEmpleadoId(codEmpleado).stream().map(this::convertToDto).collect(Collectors.toList());
		return resultado;
	}
	
	@Override
	public List<DepartamentoCargoDto> findDepartamentoCargoByEmpleadoId(Long codEmpleado) {
		List<DepartamentoCargoDto> resultado;
		
		resultado = repo.findByEmpleadoId(codEmpleado).stream().map(this::convertToCargoDto).collect(Collectors.toList());
		return resultado;
	}

	@Override
	public EmpleadoDepartamentoDto createOrUpdate(EmpleadoDepartamentoDtoInput input) {
		EmpleadoDepartamento empDep = repo.findOneByEmpleadoIdAndDepartamentoId(input.getCodEmpleado(), input.getCodDepartamento());
		if(empDep == null) empDep = new EmpleadoDepartamento();
		BeanUtils.copyProperties(input, empDep, "id");

		empDep.setDepartamento(repoDep.findById(input.getCodDepartamento())
				.orElseThrow(() -> new idNotFoundException(input.getId())));
		empDep.setEmpleado(repoEmp.findById(input.getCodEmpleado())
				.orElseThrow(() -> new idNotFoundException(input.getId())));

		EmpleadoDepartamentoDto resul = convertToDto(repo.save(empDep));
		return resul;
	}

	@Override
	public void deleteById(Long id) {
		repo.deleteById(id);
	}

	public EmpleadoDepartamentoDto convertToDto(EmpleadoDepartamento entity) {
		JMapper<EmpleadoDepartamentoDto, EmpleadoDepartamento> mapper = new JMapper<>(EmpleadoDepartamentoDto.class,
				EmpleadoDepartamento.class, jmapperApi);
		EmpleadoDepartamentoDto empleadoDepartamentoDto = mapper.getDestination(entity);
		return empleadoDepartamentoDto;
	}
	
	public DepartamentoCargoDto convertToCargoDto(EmpleadoDepartamento entity) {
		JMapper<DepartamentoCargoDto, EmpleadoDepartamento> mapper = new JMapper<>(DepartamentoCargoDto.class,
				EmpleadoDepartamento.class, jmapperApi);
		DepartamentoCargoDto departamentoCargoDto = mapper.getDestination(entity);
		departamentoCargoDto.setCodDepartamento(entity.getDepartamento().getId());
		departamentoCargoDto.setNombreDepartamento(entity.getDepartamento().getNombre());
		return departamentoCargoDto;
	}

}
