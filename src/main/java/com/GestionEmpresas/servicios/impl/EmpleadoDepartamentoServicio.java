package com.GestionEmpresas.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
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
	public EmpleadoDepartamentoDto addEmpleadoDepartamento(EmpleadoDepartamentoDtoInput eiD) {
		EmpleadoDepartamento empDep = new EmpleadoDepartamento();
		BeanUtils.copyProperties(eiD, empDep);

		empDep.setDepartamento(repoDep.findById(eiD.getDepartamento().getId())
				.orElseThrow(() -> new idNotFoundException(eiD.getId())));
		empDep.setEmpleado(
				repoEmp.findById(eiD.getEmpleado().getId()).orElseThrow(() -> new idNotFoundException(eiD.getId())));

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

}
