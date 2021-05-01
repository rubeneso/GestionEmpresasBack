package com.GestionEmpresas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestionEmpresas.dto.input.EmpresaDtoInput;
import com.GestionEmpresas.dto.response.EmpresaDto;
import com.GestionEmpresas.servicios.IEmpresaServicio;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private IEmpresaServicio serv;

	@GetMapping("/getAll")
	public ResponseEntity<Iterable<EmpresaDto>> getAllNotes() {
		return new ResponseEntity<Iterable<EmpresaDto>>(this.serv.findAll(), HttpStatus.OK);
	}

	@GetMapping("/getAll/{nPage}/{pageSize}/{sortBy}")
	public ResponseEntity<Iterable<EmpresaDto>> findAllPaginatedSort(@PathVariable(value = "nPage") Integer nPage,
			@PathVariable(value = "pageSize") Integer pageSize, @PathVariable(value = "sortBy") String sortBy) {
		
		try {
			Iterable<EmpresaDto> lista = serv.findAllPaginated(nPage, pageSize, sortBy);
			return new ResponseEntity<Iterable<EmpresaDto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<Iterable<EmpresaDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getAll/{nPage}/{pageSize}")
	public ResponseEntity<Iterable<EmpresaDto>> findAllPaginated(@PathVariable(value = "nPage") Integer nPage,
			@PathVariable(value = "pageSize") Integer pageSize) {
		
		try {
			Iterable<EmpresaDto> lista = serv.findAllPaginated(nPage, pageSize);
			return new ResponseEntity<Iterable<EmpresaDto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<Iterable<EmpresaDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getOne/{id}")
	public ResponseEntity<EmpresaDto> getEmpresaById(@PathVariable(value = "id") Long id) {
		try {
			return new ResponseEntity<EmpresaDto>(serv.findById(id), HttpStatus.OK);
		}
		catch (Exception e){
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpresaDto>(HttpStatus.NOT_FOUND);			
		}
	}

	@PostMapping("/addOrUpdate")
	public ResponseEntity<EmpresaDto> addEmpresa(@RequestBody EmpresaDtoInput empresaDtoInput) {
		try {
			return new ResponseEntity<EmpresaDto>(this.serv.addEmpresa(empresaDtoInput), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpresaDto>(HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
}
