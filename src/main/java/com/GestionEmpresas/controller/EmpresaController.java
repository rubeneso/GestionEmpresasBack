package com.GestionEmpresas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.GestionEmpresas.dto.ListaGenericDto;
import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.servicios.IEmpresaServicio;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private IEmpresaServicio service;

	@GetMapping
    public ResponseEntity<ListaGenericDto<EmpresaDto>> findAll(
        @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> orderBy) {
            
        ListaGenericDto<EmpresaDto> documentaciones = (page.isPresent() && size.isPresent())
            ? service.findAll(page.get(), size.get(), orderBy) : service.findAll(orderBy);

        return ResponseEntity.ok(documentaciones);
    }

	@GetMapping("/{id}")
	public ResponseEntity<EmpresaDto> getEmpresaById(@PathVariable(value = "id") Long id) {
		try {
			return new ResponseEntity<EmpresaDto>(service.findById(id), HttpStatus.OK);
		}
		catch (Exception e){
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpresaDto>(HttpStatus.NOT_FOUND);			
		}
	}

	@PostMapping("/createOrUpdate")
	public ResponseEntity<EmpresaDto> addEmpresa(@RequestBody EmpresaDtoInput empresaDtoInput) {
		try {
			return new ResponseEntity<EmpresaDto>(this.service.addEmpresa(empresaDtoInput), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
	public ResponseEntity<String> handleException(Exception ex)
	{
		System.err.println(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
}
