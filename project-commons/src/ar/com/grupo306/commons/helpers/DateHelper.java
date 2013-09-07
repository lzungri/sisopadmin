package ar.com.grupo306.commons.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
	public static final String DEF_DATE_FORMAT = "dd/MM/yyyy";
	
	
	public static Date textoComoFecha(String textoFecha){
		Date date;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DEF_DATE_FORMAT);
			dateFormat.setLenient(false);
			date = dateFormat.parse(textoFecha);
			return date;
		} catch (Exception e) {
			return null;
			//crear exception
		}
	}
	
	
}
