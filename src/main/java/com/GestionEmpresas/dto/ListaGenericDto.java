package com.GestionEmpresas.dto;

import java.util.List;

import com.googlecode.jmapper.annotations.JGlobalMap;

import lombok.Data;

@Data
@JGlobalMap
public class ListaGenericDto<S> {

	public Long total;
    public List<S> lista;
    
}
