package com.GestionEmpresas.servicios.impl;

import java.util.HashSet;
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
import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;
import com.GestionEmpresas.entity.Departamento;
import com.GestionEmpresas.entity.Empresa;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.repository.DepartamentoRepository;
import com.GestionEmpresas.repository.EmpresaRepository;
import com.GestionEmpresas.servicios.IEmpresaServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class EmpresaServicio implements IEmpresaServicio{

	@Autowired
	private EmpresaRepository empresaRepo;

	@Autowired
	private DepartamentoRepository departamentoRepo;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public ListaGenericDto<EmpresaDto> findAll(Optional<String> sortBy) {
		return findAll(0, Integer.MAX_VALUE, sortBy);
	}

	@Override
	public ListaGenericDto<EmpresaDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy) {
		ListaGenericDto<EmpresaDto> resultado = new ListaGenericDto<EmpresaDto>();
    	
		Pageable paging = (sortBy.isPresent()) 
				? PageRequest.of(pageNo, pageSize, Sort.by(sortBy.get()).ascending())
				: PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
    	
    	Page<Empresa> pagedResult = empresaRepo.findAll(paging);
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
	public EmpresaDto findById(Long id) {
		EmpresaDto empresaDto = convertToDto(empresaRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Empresa.class, "id", id.toString())));
		return empresaDto;
	}

	@Override
	public EmpresaDto addEmpresa(EmpresaDtoInput input) {
		if(input.getNombre() == "" || input.getNombre() == null) throw new IllegalArgumentException("Nombre no puede ser nulo o estar vacio");
		if(input.getPotenciaContratada() == null) input.setPotenciaContratada(0d);
		
		Empresa empresa = new Empresa();
		BeanUtils.copyProperties(input, empresa);

		Set<Departamento> departamentos = new HashSet<>();
		EmpresaDto empresaDto = new EmpresaDto();

		if(input.getDepartamentos() != null) {
			input.getDepartamentos().forEach(d -> departamentos
					.add(departamentoRepo.findById(d.getId())
							.orElseThrow(() -> new EntityNotFoundException(Departamento.class, "id", input.getId().toString()))));
			empresa.setDepartamentos(departamentos);
		}
		empresaDto = convertToDto(empresaRepo.save(empresa));

		return empresaDto;
	}

	public EmpresaDto convertToDto(Empresa entity) {
		JMapper<EmpresaDto, Empresa> mapper = new JMapper<>(EmpresaDto.class, Empresa.class, jmapperApi);
		EmpresaDto empresaDto = mapper.getDestination(entity);
		return empresaDto;
	}

}
