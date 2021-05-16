package com.GestionEmpresas.servicios.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;
import com.GestionEmpresas.entity.Empleado;
import com.GestionEmpresas.entity.Empresa;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.repository.EmpleadoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
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
            throw new EntityNotFoundException(Empresa.class, "all", 
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
					.orElseThrow(() -> new EntityNotFoundException(Empresa.class, "id", input.getId().toString())));			
		}

		return convertToDto(empleadoRepo.save(empleado));
	}

	private EmpleadoDto convertToDto(Empleado entity) {
		JMapper<EmpleadoDto, Empleado> mapper = new JMapper<>(EmpleadoDto.class, Empleado.class, jmapperApi);
		EmpleadoDto empleadoDto = mapper.getDestination(entity);
		return empleadoDto;
	}

}
