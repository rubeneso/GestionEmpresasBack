package com.GestionEmpresas.servicios.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.DepartamentoCargoDtoInput;
import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDepartamentoDto;
import com.GestionEmpresas.dto.response.EmpleadoDto;
import com.GestionEmpresas.entity.Empleado;
import com.GestionEmpresas.entity.EmpleadoDepartamento;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.repository.EmpleadoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
import com.GestionEmpresas.servicios.IEmpleadoDepartamentoServicio;
import com.GestionEmpresas.servicios.IEmpleadoServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {

	@Autowired
	private EmpleadoRepository empleadoRepo;

	@Autowired
	private EmpresaRepository empresaRepo;
	
	@Autowired
	private IEmpleadoDepartamentoServicio empleadoDepartamentoService;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public ListaGenericDto<EmpleadoDto> findAll(Optional<String> sortBy) {
		return findAll(0, Integer.MAX_VALUE, sortBy);
	}

	@Override
	public ListaGenericDto<EmpleadoDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy) {
		ListaGenericDto<EmpleadoDto> resultado = new ListaGenericDto<EmpleadoDto>();
    	
		Pageable paging = (sortBy.isPresent()) 
				? PageRequest.of(pageNo, pageSize, Sort.by(sortBy.get()).ascending())
				: PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
    	
    	Page<Empleado> pagedResult = empleadoRepo.findAll(paging);
        if(pagedResult.hasContent()) {
        	resultado.setTotal(pagedResult.getTotalElements());
        	resultado.setLista(pagedResult.getContent().stream().map(this::convertToDto)
                                                       .collect(Collectors.toList()));
        } else {
            throw new EntityNotFoundException(Empleado.class, "all", 
                resultado.toString());
        }
        
        return resultado;
	}

	@Override
	public EmpleadoDto findById(Long id) {
		Empleado empleado = empleadoRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Empleado.class, "id", id.toString()));
		return convertToDto(empleado);
	}

	@Override
	public EmpleadoDto createOrUpdate(EmpleadoDtoInput input) {
		if(input.getNombre() == "" || input.getNombre() == null) throw new IllegalArgumentException("Nombre no puede ser nulo ni vacio");
		
		Empleado empleado = new Empleado();
		BeanUtils.copyProperties(input, empleado);

		if(input.getCodEmpresa() != null) {
			empleado.setEmpresa(empresaRepo.findById(input.getCodEmpresa())
					.orElseThrow(() -> new EntityNotFoundException(Empleado.class, "id", input.getId().toString())));			
		}
		
		Empleado empleadoGuardado = empleadoRepo.save(empleado);
		if(input.getDepartamentosCargos() != null) {
			Set<Long> listaEmpleadosDepartamentosNuevos = new HashSet<>();
			for(DepartamentoCargoDtoInput depCargo : input.getDepartamentosCargos()) {
				 	
				EmpleadoDepartamentoDtoInput empleadoDepartamento = EmpleadoDepartamentoDtoInput.builder()
						.cargo(depCargo.getCargo())
						.codEmpleado(empleadoGuardado.getId())
						.codDepartamento(depCargo.getCodDepartamento())
						.build();
				listaEmpleadosDepartamentosNuevos.add(empleadoDepartamentoService.createOrUpdate(empleadoDepartamento).getId());
			}
			
			List<Long> listaEmpleadosDepartamentosViejos = empleadoDepartamentoService.findByEmpleadoId(empleadoGuardado.getId()).stream().map(x -> x.getId()).collect(Collectors.toList());
			for(Long empDeptId : listaEmpleadosDepartamentosViejos) {
				if(!listaEmpleadosDepartamentosNuevos.contains(empDeptId)) {
					empleadoDepartamentoService.deleteById(empDeptId);
				}
			}
			
		}

		return convertToDto(empleadoRepo.save(empleado));
	}
	
	@Override
	public void deleteById(Long codEmpleado) {
		List<Long> listaEmpleadosDepartamentos = empleadoDepartamentoService.findByEmpleadoId(codEmpleado).stream().map(x -> x.getId()).collect(Collectors.toList());
		listaEmpleadosDepartamentos.forEach(empleadoDepartamento -> empleadoDepartamentoService.deleteById(empleadoDepartamento));
		
		empleadoRepo.deleteById(codEmpleado);
	}

	private EmpleadoDto convertToDto(Empleado entity) {
		JMapper<EmpleadoDto, Empleado> mapper = new JMapper<>(EmpleadoDto.class, Empleado.class, jmapperApi);
		EmpleadoDto empleadoDto = mapper.getDestination(entity);
		
		empleadoDto.setDepartamentosCargos(empleadoDepartamentoService.findDepartamentoCargoByEmpleadoId(empleadoDto.getId()));
		
		return empleadoDto;
	}
	
	public EmpleadoDepartamentoDto convertToDto(EmpleadoDepartamento entity) {
		JMapper<EmpleadoDepartamentoDto, EmpleadoDepartamento> mapper = new JMapper<>(EmpleadoDepartamentoDto.class,
				EmpleadoDepartamento.class, jmapperApi);
		EmpleadoDepartamentoDto empleadoDepartamentoDto = mapper.getDestination(entity);
		return empleadoDepartamentoDto;
	}

}
