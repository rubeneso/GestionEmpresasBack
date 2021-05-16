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
import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.servicios.IEmpleadoServicio;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoServicio service;

	@GetMapping
    public ResponseEntity<ListaGenericDto<EmpleadoDto>> findAll(
        @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> orderBy) {
            
        ListaGenericDto<EmpleadoDto> resultado = (page.isPresent() && size.isPresent())
            ? service.findAll(page.get(), size.get(), orderBy) : service.findAll(orderBy);

        return ResponseEntity.ok(resultado);
    }

	@GetMapping("/{id}")
	public ResponseEntity<EmpleadoDto> findById(@PathVariable Long id) {
		return ResponseEntity.ok(this.service.findById(id));
	}

	@PostMapping("/createOrUpdate")
	public ResponseEntity<EmpleadoDto> createOrUpdate(@RequestBody EmpleadoDtoInput empleadoDtoInput) {
		try {
			return ResponseEntity.ok(this.service.createOrUpdate(empleadoDtoInput));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
	public ResponseEntity<String> handleException(Exception ex)
	{
		ex.printStackTrace();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
