package ar.com.grupo306.sisopadmin.persistence.tests.hibernate;

/**
 * Objeto de prueba.
 *
 * @author Leandro
 */
public class TestObject {
	private String nombre;
	private String apellido;
	private Integer edad;
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	public Integer getEdad() {
		return edad;
	}
	
	public void setEdad(Integer edad) {
		this.edad = edad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String transactionalMetodito() {
		return "Hola amiguitos!!!...";
	}

}