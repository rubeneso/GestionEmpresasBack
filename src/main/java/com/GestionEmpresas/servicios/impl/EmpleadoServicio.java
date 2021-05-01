package com.GestionEmpresas.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;
import com.GestionEmpresas.entity.Empleado;
import com.GestionEmpresas.excepciones.WrongArgsException;
import com.GestionEmpresas.excepciones.idNotFoundException;
import com.GestionEmpresas.repository.EmpleadoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
import com.GestionEmpresas.servicios.IEmpleadoServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {

	@Autowired
	private EmpleadoRepository repoEmpleado;

	@Autowired
	private EmpresaRepository repoEmpresa;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public List<EmpleadoDto> findAll() {
		ArrayList<EmpleadoDto> listaEmpleados = new ArrayList<>();
		repoEmpleado.findAll().forEach(r -> listaEmpleados.add(convertToDto(r)));
		return listaEmpleados;
	}

	@Override
	public List<EmpleadoDto> findAllPaginated(Integer pageNo, Integer pageSize, String sortBy) {
		if (pageSize < 1 || pageNo < 0 || sortBy == "") {
			throw new WrongArgsException();
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		ArrayList<EmpleadoDto> listaempleados = new ArrayList<>();

		repoEmpleado.findAll(pageable).forEach(r -> listaempleados.add(convertToDto(r)));
		return listaempleados;
	}

	@Override
	public List<EmpleadoDto> findAllPaginated(Integer pageNo, Integer pageSize) {
		if (pageSize < 1 || pageNo < 0) {
			throw new WrongArgsException();
		}

		Pageable pageable = PageRequest.of(pageNo, pageSize);
		ArrayList<EmpleadoDto> listaempleados = new ArrayList<>();

		repoEmpleado.findAll(pageable).forEach(r -> listaempleados.add(convertToDto(r)));
		return listaempleados;
	}

	@Override
	public EmpleadoDto findById(Long id) {
		EmpleadoDto empleadoDto = convertToDto(
				repoEmpleado.findById(id).orElseThrow(() -> new idNotFoundException(id)));
		return empleadoDto;
	}

	@Override
	public EmpleadoDto addEmpleado(EmpleadoDtoInput eiD) {
		if(eiD.getNombre() == "" || eiD.getNombre() == null) throw new RuntimeException("nombre");
		
		Empleado empleado = new Empleado();
		BeanUtils.copyProperties(eiD, empleado);

		EmpleadoDto empleadoDto;
		empleado.setEmpresa(
				repoEmpresa.findById(eiD.getEmpresa().getId()).orElseThrow(() -> new idNotFoundException(eiD.getId())));

		empleadoDto = convertToDto(repoEmpleado.save(empleado));
		return empleadoDto;
	}

	private EmpleadoDto convertToDto(Empleado entity) {
		JMapper<EmpleadoDto, Empleado> mapper = new JMapper<>(EmpleadoDto.class, Empleado.class, jmapperApi);
		EmpleadoDto empleadoDto = mapper.getDestination(entity);
		return empleadoDto;
	}

}
