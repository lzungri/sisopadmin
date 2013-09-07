package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.controlador;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;

public class ControladorPlantillas {
	String porcentajeMB;//Muy bueno
	String porcentajeB;//Bueno
	String porcentajeR;//Regular
	String porcentajeI;//Insuficiente
	
	public boolean generarResultado(Collection puntos){
		if(puntos == null || puntos.isEmpty())
			return false;
		Iterator iPuntos = puntos.iterator();
		Long total = new Long(0);
		Long cantidadMB = new Long(0);
		Long cantidadB = new Long(0);
		Long cantidadR = new Long(0);
		Long cantidadI = new Long(0);
		Long limiteSupI=new Long(3);
		Long limiteSupR=new Long(5);
		Long limiteSupB=new Long(7);
		Long UNIDAD = new Long(1);
		while(iPuntos.hasNext()){
			PuntoEncuestado punto = (PuntoEncuestado)iPuntos.next();
			Long puntuacion = punto.getPuntuacion();
			total = total + UNIDAD;
			if(puntuacion.compareTo(limiteSupI)<=0){
				cantidadI = cantidadI + UNIDAD;
				continue;
			}
			if(puntuacion.compareTo(limiteSupR)<=0){
				cantidadR = cantidadR + UNIDAD;
				continue;
			}
			if(puntuacion.compareTo(limiteSupB)<=0){
				cantidadB = cantidadB + UNIDAD;
				continue;
			}
			cantidadMB = cantidadMB + UNIDAD;
			
		}
		String SIMBOLO_PORCENTAJE =  "%";
		this.setPorcentajeB(this.calcularPorcentaje(total, cantidadB).toString());
		this.setPorcentajeI(this.calcularPorcentaje(total, cantidadI).toString());
		this.setPorcentajeMB(this.calcularPorcentaje(total, cantidadMB).toString());
		this.setPorcentajeR(this.calcularPorcentaje(total, cantidadR).toString());
		return true;
	}
	
	private Float calcularPorcentaje(Long total , Long parcial){
		if(total.compareTo(new Long(0))==0)
			return new Float(0);
		BigDecimal totalB = new BigDecimal(total.intValue());
		BigDecimal parcialB = new BigDecimal(parcial.intValue());
		BigDecimal CIEN = new BigDecimal(100);
		
		
		return new Float(parcialB.multiply(CIEN).divide(totalB, 2, BigDecimal.ROUND_HALF_DOWN).floatValue());
	}
	
	
	
	
	public String getPorcentajeB() {
		return porcentajeB;
	}
	public void setPorcentajeB(String porcentajeB) {
		this.porcentajeB = porcentajeB;
	}
	public String getPorcentajeI() {
		return porcentajeI;
	}
	public void setPorcentajeI(String porcentajeI) {
		this.porcentajeI = porcentajeI;
	}
	public String getPorcentajeMB() {
		return porcentajeMB;
	}
	public void setPorcentajeMB(String porcentajeMB) {
		this.porcentajeMB = porcentajeMB;
	}
	public String getPorcentajeR() {
		return porcentajeR;
	}
	public void setPorcentajeR(String porcentajeR) {
		this.porcentajeR = porcentajeR;
	}
}
