package com.GestionEmpresas.controller;

import java.util.List;

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

import com.GestionEmpresas.dto.input.EmpleadoDtoInput;
import com.GestionEmpresas.dto.response.EmpleadoDto;
import com.GestionEmpresas.servicios.IEmpleadoServicio;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoServicio serv;

	@GetMapping("/getAll")
	public ResponseEntity<List<EmpleadoDto>> getAllNotes() {
		try {
			return new ResponseEntity<List<EmpleadoDto>>(this.serv.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass() + " " + e.getMessage());
			return new ResponseEntity<List<EmpleadoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll/{nPage}/{pageSize}/{sortBy}")
	public ResponseEntity<Iterable<EmpleadoDto>> findAllPaginatedSort(@PathVariable(value = "nPage") Integer nPage,
			@PathVariable(value = "pageSize") Integer pageSize, @PathVariable(value = "sortBy") String sortBy) {

		try {
			Iterable<EmpleadoDto> lista = serv.findAllPaginated(nPage, pageSize, sortBy);
			return new ResponseEntity<Iterable<EmpleadoDto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass() + " " + e.getMessage());
			return new ResponseEntity<Iterable<EmpleadoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll/{nPage}/{pageSize}")
	public ResponseEntity<Iterable<EmpleadoDto>> findAllPaginated(@PathVariable(value = "nPage") Integer nPage,
			@PathVariable(value = "pageSize") Integer pageSize) {

		try {
			Iterable<EmpleadoDto> lista = serv.findAllPaginated(nPage, pageSize);
			return new ResponseEntity<Iterable<EmpleadoDto>>(lista, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass() + " " + e.getMessage());
			return new ResponseEntity<Iterable<EmpleadoDto>>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getOne/{id}")
	public ResponseEntity<EmpleadoDto> getEmpleadoById(@PathVariable(value = "id") Long id) {
		try {
			return new ResponseEntity<EmpleadoDto>(this.serv.findById(id), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e.getClass() + " " + e.getMessage());
			return new ResponseEntity<EmpleadoDto>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addOrUpdate")
	public ResponseEntity<EmpleadoDto> addEmpleado(@RequestBody EmpleadoDtoInput empleadoDtoInput) {
		try {
			return new ResponseEntity<EmpleadoDto>(this.serv.addEmpleado(empleadoDtoInput), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<EmpleadoDto>(HttpStatus.FAILED_DEPENDENCY);
		}

	}

}
