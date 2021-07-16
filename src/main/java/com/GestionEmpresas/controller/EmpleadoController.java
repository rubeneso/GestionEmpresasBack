package com.GestionEmpresas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.PropertyReferenceException;
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
	private IEmpleadoServicio empleadoService;

	@GetMapping
    public ResponseEntity<ListaGenericDto<EmpleadoDto>> findAll(
        @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> orderBy) {
            
        ListaGenericDto<EmpleadoDto> resultado = (page.isPresent() && size.isPresent())
            ? empleadoService.findAll(page.get(), size.get(), orderBy) : empleadoService.findAll(orderBy);

        return ResponseEntity.ok(resultado);
    }

	@GetMapping("/{id}")
	public ResponseEntity<EmpleadoDto> findById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(this.empleadoService.findById(id));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/createOrUpdate")
	public ResponseEntity<EmpleadoDto> createOrUpdate(@RequestBody EmpleadoDtoInput input) {
		return ResponseEntity.ok(this.empleadoService.createOrUpdate(input));
	}
	
	@GetMapping("/delete/{id}")
	public void deleteById(@PathVariable Long id) {
		this.empleadoService.deleteById(id);
	}
	
	@ExceptionHandler({PropertyReferenceException.class, IllegalArgumentException.class})
	public ResponseEntity<String> handleException(Exception ex)
	{
		ex.printStackTrace();
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException ex)
	{
		return ResponseEntity.noContent().build();
	}

}
