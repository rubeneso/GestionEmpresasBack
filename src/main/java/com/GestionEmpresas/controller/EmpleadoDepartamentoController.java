package com.GestionEmpresas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.GestionEmpresas.dto.input.EmpleadoDepartamentoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDepartamentoDto;
import com.GestionEmpresas.servicios.IEmpleadoDepartamentoServicio;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/empleadoDepartamento")
public class EmpleadoDepartamentoController {
	
	@Autowired
	IEmpleadoDepartamentoServicio empleadoDepartamentoService;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<EmpleadoDepartamentoDto>> getAllNotes() {
		try {
			return new ResponseEntity<List<EmpleadoDepartamentoDto>>(empleadoDepartamentoService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<List<EmpleadoDepartamentoDto>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getOne/{id}")
	public ResponseEntity<EmpleadoDepartamentoDto> getEmpleadoDepartamento(@PathVariable(value = "id") Long id) {
		try {
			return new ResponseEntity<EmpleadoDepartamentoDto>(empleadoDepartamentoService.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpleadoDepartamentoDto>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/addOrUpdate")
	public ResponseEntity<EmpleadoDepartamentoDto> addEmpleadoDepartamento(@RequestBody EmpleadoDepartamentoDtoInput empDepDtoInput) {
		try {
			return new ResponseEntity<EmpleadoDepartamentoDto>(this.empleadoDepartamentoService.createOrUpdate(empDepDtoInput), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpleadoDepartamentoDto>(HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<EmpleadoDepartamentoDto> deleteDepartamento(@PathVariable(value = "id") Long id) {
		try {
			this.empleadoDepartamentoService.deleteById(id);
			return new ResponseEntity<EmpleadoDepartamentoDto>(HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass()+" "+e.getMessage());
			return new ResponseEntity<EmpleadoDepartamentoDto>(HttpStatus.NOT_FOUND);
		}
	}
}
