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

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7918010297556162716L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String nombre;
	private String descripcion;
	private Double potenciaContratada;
	
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
			name = "EmpresaDepartamento",
			joinColumns = { @JoinColumn(name = "codEmpresa") }, 
			inverseJoinColumns = { @JoinColumn(name = "codDepartamento") }
		)
    private Set<Departamento> departamentos = new HashSet<>();
	
	public void addDepartamentos(Set<Departamento> departamentos) {
		this.departamentos.forEach(departamento -> this.departamentos.add(departamento));
	}
	
	public void removeDepartamento(Departamento departamento) {
		this.departamentos.remove(departamento);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
