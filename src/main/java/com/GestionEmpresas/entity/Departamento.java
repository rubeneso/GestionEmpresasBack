package com.GestionEmpresas.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Departamento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5412804977240611897L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String nombre;
	private String descripcion;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
		name = "EmpresaDepartamento",
		joinColumns = { @JoinColumn(name = "codDepartamento") }, 
		inverseJoinColumns = { @JoinColumn(name = "codEmpresa") }
	)
	private Set<Empresa> empresas = new HashSet<>();
	
	@OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EmpleadoDepartamento> empleados = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
