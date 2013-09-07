package ar.com.grupo306.sisopadmin.domain.validation.validadores;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public interface Validable {
	public boolean validate(ModelObject objetoModelo);
	public String toString();
}
