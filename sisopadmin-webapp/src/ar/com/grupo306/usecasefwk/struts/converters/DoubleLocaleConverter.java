package ar.com.grupo306.usecasefwk.struts.converters;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DoubleConverter;

/**
 * Convierte un Object a Double.
 * La principal responsabilidad de esta clase que lo diferencia del DoubleConverter
 * es que considera la Locale es_Ar para la conversión.
 * 
 * <br>Ejemplo: 
 * <li> "1,1" lo convertirá a "1.1" y luego invocará la misma lógica que DoubleConverter.
 * <li> "1.1" ejecutará la lógica del DoubleConverter.
 * 
 * @see org.apache.commons.beanutils.converters.DoubleConverter
 *
 * @author lzungri
 */
public class DoubleLocaleConverter implements Converter {
    private Object defaultValue = null;
    private boolean useDefault = true;
	
	public DoubleLocaleConverter() {
        this.defaultValue = null;
        this.useDefault = false;
    }

	public DoubleLocaleConverter(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.useDefault = true;
    }

    public Object convert(Class type, Object value) {
    	// Aquí se realiza la conversión.
    	Object finalValue = value != null ? value.toString().replaceAll(",", ".") : null;
    	
    	DoubleConverter converter = this.useDefault ? new DoubleConverter(this.defaultValue) : new DoubleConverter();  
    	
    	return converter.convert(type, finalValue);
    }
}