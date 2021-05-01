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

import com.GestionEmpresas.dto.input.DepartamentoDtoInput;
import com.GestionEmpresas.dto.response.DepartamentoDto;
import com.GestionEmpresas.servicios.IDepartamentoServicio;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/departamento")
public class DepartamentoController {
	
	@Autowired
	private IDepartamentoServicio serv;

	@GetMapping("/getAll")
	public ResponseEntity<Iterable<DepartamentoDto>> getAllNotes() {
		try {
			return new ResponseEntity<Iterable<DepartamentoDto>>(this.serv.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<Iterable<DepartamentoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<DepartamentoDto> getDepartamentoById(@PathVariable(value = "id") Long id) {
		try {
			return new ResponseEntity<DepartamentoDto>(serv.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<DepartamentoDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addOrUpdate")
	public ResponseEntity<DepartamentoDto> addDepartamento(@RequestBody DepartamentoDtoInput DepartamentoDtoInput) {
		try {
			return new ResponseEntity<DepartamentoDto>(this.serv.addDepartamento(DepartamentoDtoInput), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<DepartamentoDto>(HttpStatus.FAILED_DEPENDENCY);
		}
	}

}
