package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.ArrayList;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

/**
 * 
 * @author Rafa
 *
 */

public class Tp extends ModelObject{
	
	
	
	String nombre;
	ArrayList fases;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList getFases() {
		return fases;
	}

	public void setFases(ArrayList fases) {
		this.fases = fases;
	}
}
