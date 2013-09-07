package ar.com.grupo306.sisopadmin.domain.modelo.impl;


/**
 * ModelObject que tiene un c�digo con significado en el dominio de la
 * aplicaci�n.
 * 
 * Este c�digo podr� ser utilizado para realizar la b�squeda de instancias
 * por el usuario, es decir, existe el id de un ModelObject, pero este es
 * propio de la persistencia y el usuario no deber�a conocer el identificador
 * con el cual se lo almacena en la base, sino que debe tener un c�digo
 * que tenga un significado para el dominio.
 * Mediante ese c�digo se identifica un�vocamente a una instancia.
 * 
 * Un Objeto hoy podr�a almacenarse con el id 1, pero el d�a de ma�ana 
 * al popular la base (si el id se genera autom�ticamente) podr�a tener el 20,
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