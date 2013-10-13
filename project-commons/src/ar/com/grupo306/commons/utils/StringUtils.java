package ar.com.grupo306.commons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.commons.collections.Transformer;

public class StringUtils extends org.apache.commons.lang.StringUtils
{

  public static String formatList(Iterator elementsIterator, Transformer transformer, String start, String separator, String end)
  {
    StringBuffer buffer = new StringBuffer(start);
    while (elementsIterator.hasNext())
    {
      buffer.append(transformer.transform(elementsIterator.next()));
      if (elementsIterator.hasNext())
      {
        buffer.append(separator);
      }
    }
    return buffer.append(end).toString();
  }
  
  public static String formatStackTrace(Throwable _throwable) {
    StringWriter st = new StringWriter();
    _throwable.printStackTrace(new PrintWriter(st));
    String stackTrace = st.toString();
    stackTrace = StringUtils.replace(stackTrace, "\n", "<BR>\n");
    return stackTrace;
  }
    
	/**
	 * Retorna la posición del string donde se ubica el <code>pattern</code> 
	 * repetido por <code>patternCounts</code> vez.
	 * Ejemplo:
	 * <li> "aa.bb.cc.dd.ee", pattern = ".", patternCount = 3 -> 8
	 * <li> "aa.bb.cc.dd.ee", pattern = "c", patternCount = 2 -> 7 
	 * 
	 * @see String#indexOf(java.lang.String)
	 * 
	 * @param str
	 * @param pattern: Patter a buscar.
	 * @param patternCounts: Cantidad de veces que saltará el pattern.
	 * @return int
	 */
	public static int positionOf(String str, String pattern, int patternCounts) {
		int index = 0;
		int count = 0;
		
		for(; count < patternCounts && str.indexOf(pattern, index) > 0; ++count) {
			index = str.indexOf(pattern, index) + 1;
		}

		/*
		 * Para trabajar de manera uniforme al indexOf se retorna -1 si no se encuentra.
		 */
		if(count >= patternCounts) {
			return index - 1;
		}
		return -1;
	}
    
}
