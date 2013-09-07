package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.util.ArrayList;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class PuntoAEncuestar extends ModelObject{
	ArrayList puntosEncuestados;
	String descripcion;
	String nombre;
	Long numero;
	Integer peso;
	PlantillaEncuesta plantillaEncuesta;
	
	
	public PlantillaEncuesta getPlantillaEncuesta() {
		return plantillaEncuesta;
	}
	public void setPlantillaEncuesta(PlantillaEncuesta plantillaEncuesta) {
		this.plantillaEncuesta = plantillaEncuesta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public ArrayList getPuntosEncuestados() {
		return puntosEncuestados;
	}
	public void setPuntosEncuestados(ArrayList puntosEncuestados) {
		this.puntosEncuestados = puntosEncuestados;
	}
}
