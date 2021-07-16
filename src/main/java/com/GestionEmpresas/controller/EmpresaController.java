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
import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;
import com.GestionEmpresas.excepciones.EntityNotFoundException;
import com.GestionEmpresas.servicios.IEmpresaServicio;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private IEmpresaServicio empresaService;

	@GetMapping
    public ResponseEntity<ListaGenericDto<EmpresaDto>> findAll(
        @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> orderBy) {
            
        ListaGenericDto<EmpresaDto> resultado = (page.isPresent() && size.isPresent())
            ? empresaService.findAll(page.get(), size.get(), orderBy) : empresaService.findAll(orderBy);

        return ResponseEntity.ok(resultado);
    }

	@GetMapping("/{id}")
	public ResponseEntity<EmpresaDto> findById(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(empresaService.findById(id));
		}
		catch (EntityNotFoundException e){
			e.printStackTrace();
			return ResponseEntity.notFound().build();			
		}
	}

	@PostMapping("/createOrUpdate")
	public ResponseEntity<EmpresaDto> createOrUpdate(@RequestBody EmpresaDtoInput input) {
		return ResponseEntity.ok(this.empresaService.createOrUpdate(input));
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
