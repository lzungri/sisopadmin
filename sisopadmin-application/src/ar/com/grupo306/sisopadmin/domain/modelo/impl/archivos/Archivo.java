package ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;

/**
 * @author Damián
 * 
 * Representa un puntero a los archivos subidos al sistema.
 * 
 */

public class Archivo extends ModelObject {
	String ruta;
	String nombre;
	String descripcion;
	Usuario usuarioCreador;
	
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	
}
