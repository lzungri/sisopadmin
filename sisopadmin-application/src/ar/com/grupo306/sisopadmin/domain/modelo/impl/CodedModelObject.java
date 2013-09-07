package ar.com.grupo306.sisopadmin.domain.modelo.impl;


/**
 * ModelObject que tiene un código con significado en el dominio de la
 * aplicación.
 * 
 * Este código podrá ser utilizado para realizar la búsqueda de instancias
 * por el usuario, es decir, existe el id de un ModelObject, pero este es
 * propio de la persistencia y el usuario no debería conocer el identificador
 * con el cual se lo almacena en la base, sino que debe tener un código
 * que tenga un significado para el dominio.
 * Mediante ese código se identifica unívocamente a una instancia.
 * 
 * Un Objeto hoy podría almacenarse con el id 1, pero el día de mañana 
 * al popular la base (si el id se genera automáticamente) podría tener el 20,
 * entonces es necesario contar con un identificador independiente de la
 * persistencia.
 *
 * @author Leandro
 */
public class CodedModelObject extends ModelObject {
	private String domainCode;

	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
}