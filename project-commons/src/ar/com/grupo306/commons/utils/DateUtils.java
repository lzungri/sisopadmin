package ar.com.grupo306.commons.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utilidades para manejo de fechas.
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	//formatos de date
	public static String YEAR_LONG_FORMAT = "yyyy";
	public static String YEAR_SHORT_FORMAT = "y";
	public static String MONTH_LONG_FORMAT = "MM";
	public static String MONTH_SHORT_FORMAT = "M";
	public static String DAY_LONG_FORMAT = "dd";
	public static String DAY_SHORT_FORMAT = "d";
	
	//formatos de time
	public static String HOUR_FORMAT = "HH";
	public static String MINUTE_LONG_FORMAT = "mm";
	public static String MINUTE_SHORT_FORMAT = "m";
	public static String SECOND_LONG_FORMAT = "s";
	public static String SECOND_SHORT_FORMAT = "ss";
	
	/**
     * Formato de fechas utilizado por Java.
     * @see java.util.Date#toString()
     */
	public static String JAVA_DATE_FORMAT = "EEE MMM dd hh:mm:ss zzz yyyy";

    /**
	 * No se permite instanciar esta clase.
	 */
	private DateUtils() {
		
	}
	
	/**
	 * Devuelve la fecha y hora actual.
	 * @return fecha actual
	 */
	public static Date getToday() {
		return new Date();
	}
	
	/**
	 * Obtiene la diferencia en días entre dos fechas. (jcengia)
	 * @param date1 una fecha
	 * @param date2 otra fecha
	 * @return cantidad (entero positivo) de días que separan una fecha de otra.
	 */
	public static long subtract(Date date1, Date date2) {
		Calendar cal1, cal2;
		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		long subtraction = cal1.getTimeInMillis() - cal2.getTimeInMillis();
		subtraction /= (24 * 60 * 60 * 1000);
		return Math.abs(subtraction);
	}
	
	/**
	 * Incrementa a una fecha una determinada cantidad de días.(jcengia)
	 * @param oldDate fecha a incrementar.
	 * @param days cantidad de días a incrementar.
	 * @return nueva fecha obtenida de incrementar oldDate en days días. 
	 */
	public static Date increase(Date oldDate, int days) {
		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(oldDate);
		myCalendar.add(Calendar.DATE, days);
		java.util.Date newDate = myCalendar.getTime();
		return newDate;
	}
	
	/**
	 * Incrementa una fecha <code>date</code> en una cantidad de meses
	 * <code>months</code>
	 * 
	 * @param date: Fecha a incrementar.
	 * @param months: Meses a incrementar o decrementar(número negativo).
	 */
	public static Date increaseMonths(Date date, int months) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	
/**
 * Retorna un nuevo dia con la suma de milisegundos que se
 * haya enviado.
 * 
 * @param date El dia la cual se le suman los milisegundos
 * @param millisecond Cantidad de milisegundos a sumar
 * @return Un nuevo dia en base al dia original + cantidad de milisegundos
 */
	public static Date addTimeMillisecond(Date date, long millisecond){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		long newDateMilliseconds = calendar.getTimeInMillis() + millisecond;
		
		return new Date(newDateMilliseconds);
	}
	
	/**
	 * Retorna un string que resulta de la trasformacion del 
	 * date en base al formato especificado.<br/>
	 * 
	 * <pre>
	 *  Ejemplo:<br/>
	 *  
	 *  StringBuffer format = new StringBuffer();
	 *
	 *	format.append(DateUtils.YEAR_LONG_FORMAT).append(" ")
	 *		  .append(DateUtils.MONTH_LONG_FORMAT).append(" ")
	 *		  .append(DateUtils.DAY_LONG_FORMAT);
	 *
	 *   String yyyymmdd = DateUtils.getDateTimeFormated(DateUtils.getToday(), format.toString());
	 *   
	 *   yyyymmdd = {"2005 08 12"}
	 * </pre>
	 * 
	 * @param date El dia a aplicar el formato
	 * @param format El formato a aplicar
	 * @return String con el dia formateado
	 * @see DateUtils#YEAR_LONG_FORMAT
	 * @see DateUtils#YEAR_SHORT_FORMAT
	 * @see DateUtils#MONTH_LONG_FORMAT
	 * @see DateUtils#MONTH_SHORT_FORMAT
	 * @see DateUtils#DAY_LONG_FORMAT
	 * @see DateUtils#DAY_SHORT_FORMAT
	 */
	public static String getDateTimeFormat(Date date, String format){
		return getDateTimeFormat(date, format, new Locale("es"));
	}
	
	/**
	 * Retorna un string que resulta de la trasformacion del <tt>
	 * Date</tt> en base al formato especificado, y el idioma y país 
	 * especificaso con <tt>Locale</tt>.
	 * 
	 * @param date Fecha a la que se aplica el formato
	 * @param format Formato a aplicar
	 * @param locale Para especificar el código de idioma y país
	 * @return
	 */
	public static String getDateTimeFormat(Date date, String format, Locale locale){
		SimpleDateFormat par = new SimpleDateFormat(format, locale);		
   	    return par.format(date);
	}
	
	/**
	 * @param date
	 * @param pattern
	 * @return un objeto Date resultado de parsear el parametro stringDate con el formato format
	 * @throws ParseException
	 * <br>nbarrera
	 */
	public static Date getDateFromString(String date, String pattern) throws ParseException{
		return getDateFromString(date, pattern, new Locale("es"));
	}
    
    /**
     * Crea una fecha a partir del parseo de un string que la representa con un formato determinado.
     * @param date fecha representada como string.
     * @param pattern describe el formato del string que representa una fecha.
     * @param locale Para especificar el código de idioma y país.
     * @return instancia de <tt>Date</tt> parseada del string date.
     * @throws ParseException
     */
    public static Date getDateFromString(String date, String pattern, Locale locale) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
        return formatter.parse(date);
    }
	
	/**
	 * Devuelve la fecha actual, pero exactamente en el comienzo del día. Es
	 * decir, hoy a las 0 horas, 0 minutos, 0 segundos y 0 milésimas de segundo.
	 * 
	 * @return fecha de hoy con hora 0:00:00:000.
	 */
	public static Date getTodayBeginning() {
		return getPlainDate(getToday());
	}
	
	/**
	 * Devuelve la fecha pasada por parametro, pero con 
	 * la hora cambiada para el final del dia.
	 * 
	 * @return fecha con hora 23:59:59:999.
	 */
	public static Date getDateEnding(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * Devuelve la fecha recibida en el parámetro <code>sourceDate</code>,
	 * pero exactamente en el comienzo del día. Es decir, a las 0 horas, 
	 * 0 minutos, 0 segundos y 0 milésimas de segundo.
	 * 
	 * @parameter sourceDate Fecha a partir de la cual se calcula la fecha pura. 
	 * @return fecha de hoy con hora 0:00:00:000.
	 */
	public static Date getPlainDate(Date sourceDate) {
		return getPlainCalendar(sourceDate).getTime();
	}

	
	private static Calendar getPlainCalendar(Date sourceDate) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(sourceDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
				
	}
    
    /**
     * Dada una fecha cualquiera se fija que mes es y cual es el ultimo
     * dia de ese mes, devolviendo una NUEVA fecha que es la de ese dia
     * 
     * Hasta ahora solo se esta usando en VISA consultar debitos automaticos
     * 
     * @param aDate - una fecha
     * @return un Date que representa el ultimo dia del mes y anio del 
     * date que vino como parametro
     */
    public static Date getLastDayOfMonth(Date aDate){
        Calendar cal = getPlainCalendar(aDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * Dada una fecha cualquiera se fija que mes es y cual es el primer
     * dia de ese mes, devolviendo una NUEVA fecha que es la de ese dia
     * 
     * @param aDate - una fecha
     * @return un Date que representa el primer dia del mes y anio del 
     * date que vino como parametro
     */
    public static Date getFirstDayOfMonth(Date aDate){
        Calendar cal = getPlainCalendar(aDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * Cambia el formato de una fecha representada con un <tt>String</tt>.  (jcengia)
     * @param date fecha a modificar
     * @param currentFormat formato actual de la fecha
     * @param newFormat nuevo formato
     * @return <tt>String</tt> que representa a la misma fecha con el nuevo formato.
     * @throws ParseException
     */
    public static String changeDateFormat(String date, String currentFormat, String newFormat) throws ParseException {
        Date actualDate = getDateFromString(date, currentFormat);
        return getDateTimeFormat(actualDate, newFormat);    
    }
    
    /**
    *
    * @param fecha
    * @param desde fecha desde la cual se genera el rango
    * @param hasta fecha final del rango
    * @return
    */
   public static boolean isInRange(Date fecha , Timestamp desde , Timestamp hasta){
     int diaFecha = fecha.getDate();
     int mesFecha = fecha.getMonth();
     int anioFecha= fecha.getYear();
     boolean desdeOk = desde.getYear() < anioFecha || (desde.getYear() == anioFecha && desde.getMonth() < mesFecha) || (desde.getYear() == anioFecha && desde.getMonth() == mesFecha && desde.getDate() <= diaFecha) ;
     boolean hastaOk = hasta.getYear() > anioFecha || (hasta.getYear() == anioFecha && hasta.getMonth() > mesFecha) || (hasta.getYear() == anioFecha && hasta.getMonth() == mesFecha && hasta.getDate() >= diaFecha);

     return desdeOk && hastaOk;
   }
   
   public static boolean isInRange(Date fecha , Date desde , Date hasta){
	     int diaFecha = fecha.getDate();
	     int mesFecha = fecha.getMonth();
	     int anioFecha= fecha.getYear();
	     boolean desdeOk = desde.getYear() < anioFecha || (desde.getYear() == anioFecha && desde.getMonth() < mesFecha) || (desde.getYear() == anioFecha && desde.getMonth() == mesFecha && desde.getDate() <= diaFecha) ;
	     boolean hastaOk = hasta.getYear() > anioFecha || (hasta.getYear() == anioFecha && hasta.getMonth() > mesFecha) || (hasta.getYear() == anioFecha && hasta.getMonth() == mesFecha && hasta.getDate() >= diaFecha);

	     return desdeOk && hastaOk;
	   }
   
   

   /**
    *
    * @param fecha fecha a evaluar
    * @param hasta fecha que marca el extremo mayor del rango
    * @return
    */
   public static boolean isGreaterThanRange(Date fecha , Timestamp hasta){
     int diaFecha = fecha.getDate();
     int mesFecha = fecha.getMonth();
     int anioFecha= fecha.getYear();

     boolean hastaOk = (hasta.getDate() < diaFecha && hasta.getMonth() == mesFecha && hasta.getYear() == anioFecha) ||
     (hasta.getMonth() < mesFecha && hasta.getYear() == anioFecha) || 
     hasta.getYear() < anioFecha;

     return hastaOk;
   }
   
   public static boolean isGreaterThanRange(Date fecha , Date hasta){
	     int diaFecha = fecha.getDate();
	     int mesFecha = fecha.getMonth();
	     int anioFecha= fecha.getYear();

	     boolean hastaOk = (hasta.getDate() < diaFecha && hasta.getMonth() == mesFecha && hasta.getYear() == anioFecha) ||
	     (hasta.getMonth() < mesFecha && hasta.getYear() == anioFecha) || 
	     hasta.getYear() < anioFecha;

	     return hastaOk;
	   }
   
   public static boolean isSameDayMonthYear(Date fecha , Date hasta){
	     int diaFecha = fecha.getDate();
	     int mesFecha = fecha.getMonth();
	     int anioFecha= fecha.getYear();

	     boolean hastaOk = (hasta.getDate() == diaFecha && hasta.getMonth() == mesFecha && hasta.getYear() == anioFecha);
	     
	     return hastaOk;
	   }
	
}
