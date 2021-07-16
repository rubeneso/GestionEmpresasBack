package com.GestionEmpresas.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class Empleado implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 383780986969501146L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	@NotNull
	private String nombre;
	private String descripcion;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "codEmpresa")
	private Empresa empresa;
	
	@OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EmpleadoDepartamento> empleadosDepartamentos = new HashSet<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
