package com.GestionEmpresas.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class EmpleadoDepartamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4957331489836524583L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cargo;

	@ManyToOne
	@JoinColumn(name = "codEmpleado")
	private Empleado empleado;

	@ManyToOne
	@JoinColumn(name = "codDepartamento")
	private Departamento departamento;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmpleadoDepartamento other = (EmpleadoDepartamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
