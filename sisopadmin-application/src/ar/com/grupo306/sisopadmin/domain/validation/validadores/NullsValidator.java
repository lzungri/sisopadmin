package ar.com.grupo306.sisopadmin.domain.validation.validadores;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class NullsValidator implements Validable{

	public boolean validate(ModelObject objetoModelo) {
		
		return objetoModelo.validarNulls();
	}
	
	public String toString(){
		return "NullsValidator";
	}

}
