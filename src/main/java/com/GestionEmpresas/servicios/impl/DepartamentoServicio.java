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
import com.GestionEmpresas.dto.input.DepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoDto;
import com.GestionEmpresas.entity.Departamento;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.repository.DepartamentoRepository;
import com.GestionEmpresas.servicios.IDepartamentoServicio;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;

@Service
public class DepartamentoServicio implements IDepartamentoServicio {

	@Autowired
	private DepartamentoRepository departamentoRepo;

	@Autowired
	private JMapperAPI jmapperApi;

	@Override
	public ListaGenericDto<DepartamentoDto> findAll(Optional<String> sortBy) {
		return findAll(0, Integer.MAX_VALUE, sortBy);
	}

	@Override
	public ListaGenericDto<DepartamentoDto> findAll(Integer pageNo, Integer pageSize, Optional<String> sortBy) {
		ListaGenericDto<DepartamentoDto> resultado = new ListaGenericDto<DepartamentoDto>();
    	
		Pageable paging = (sortBy.isPresent()) 
				? PageRequest.of(pageNo, pageSize, Sort.by(sortBy.get()).ascending())
				: PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
    	
    	Page<Departamento> pagedResult = departamentoRepo.findAll(paging);
        if(pagedResult.hasContent()) {
        	resultado.setTotal(pagedResult.getTotalElements());
        	resultado.setLista(pagedResult.getContent().stream().map(this::convertToDto)
                                                       .collect(Collectors.toList()));
        } else {
            throw new EntityNotFoundException(Departamento.class, "all", 
                resultado.toString());
        }
        
        return resultado;
	}

	@Override
	public DepartamentoDto findById(Long id) {
		DepartamentoDto departamento = convertToDto(departamentoRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Departamento.class, "id", id.toString())));
		return departamento;
	}

	@Override
	public DepartamentoDto createOrUpdate(DepartamentoDtoInput input) {
		Departamento departamento = new Departamento();
		BeanUtils.copyProperties(input, departamento);
		
		return convertToDto(departamentoRepo.save(departamento));
	}

	private DepartamentoDto convertToDto(Departamento entity) {
		JMapper<DepartamentoDto, Departamento> mapper = new JMapper<>(DepartamentoDto.class, Departamento.class, jmapperApi);
		DepartamentoDto departamentoDto = mapper.getDestination(entity);
		return departamentoDto;
	}

}
