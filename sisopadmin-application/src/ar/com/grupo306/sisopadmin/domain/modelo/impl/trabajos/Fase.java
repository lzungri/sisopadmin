package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.Collection;
import java.util.Date;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class Fase extends ModelObject{
	boolean entregaObligatoria;
	Date fechaFin;
	Date fechaInicio;
	String nombre;
	int numero;
	Collection plantillasCorreccion;
	Collection consignas;
	
	
	public boolean isEntregaObligatoria() {
		return entregaObligatoria;
	}
	public void setEntregaObligatoria(boolean entregaObligatoria) {
		this.entregaObligatoria = entregaObligatoria;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Collection getConsignas() {
		return consignas;
	}
	public void setConsignas(Collection consignas) {
		this.consignas = consignas;
	}
	public Collection getPlantillasCorreccion() {
		return plantillasCorreccion;
	}
	public void setPlantillasCorreccion(Collection plantillasCorreccion) {
		this.plantillasCorreccion = plantillasCorreccion;
	}
}
