package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.controlador.ControladorPlantillas;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class PuntoAEncuestar extends ModelObject{
	Collection puntosEncuestados;
	String descripcion;
	String nombre;
	Long numero;
	Integer peso;
	PlantillaEncuesta plantillaEncuesta;
	ControladorPlantillas controlador = new ControladorPlantillas();
	BufferedImage image;
	String cantidadEncuestados;
	BigDecimal porcentajeMB;
	BigDecimal porcentajeI;
	BigDecimal porcentajeR;
	BigDecimal porcentajeB;
	JRBeanCollectionDataSource arrayTorta;
	
	
	public String getCantidadEncuestados() {
		return cantidadEncuestados;
	}
	public void setCantidadEncuestados(String cantidadEncuestados) {
		this.cantidadEncuestados = cantidadEncuestados;
	}
	public PlantillaEncuesta getPlantillaEncuesta() {
		return plantillaEncuesta;
	}
	public void setPlantillaEncuesta(PlantillaEncuesta plantillaEncuesta) {
		this.plantillaEncuesta = plantillaEncuesta;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public Collection getPuntosEncuestados() {
		return puntosEncuestados;
	}

	public ControladorPlantillas getControlador() {
		return controlador;
	}
	public void setControlador(ControladorPlantillas controlador) {
		this.controlador = controlador;
	}
	public void setPuntosEncuestados(Collection puntosEncuestados) {
		this.puntosEncuestados = puntosEncuestados;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public BigDecimal getPorcentajeB() {
		return porcentajeB;
	}
	public void setPorcentajeB(BigDecimal porcentajeB) {
		this.porcentajeB = porcentajeB;
	}
	public BigDecimal getPorcentajeI() {
		return porcentajeI;
	}
	public void setPorcentajeI(BigDecimal porcentajeI) {
		this.porcentajeI = porcentajeI;
	}
	public BigDecimal getPorcentajeMB() {
		return porcentajeMB;
	}
	public void setPorcentajeMB(BigDecimal porcentajeMB) {
		this.porcentajeMB = porcentajeMB;
	}
	public BigDecimal getPorcentajeR() {
		return porcentajeR;
	}
	public void setPorcentajeR(BigDecimal porcentajeR) {
		this.porcentajeR = porcentajeR;
	}
	public JRBeanCollectionDataSource getArrayTorta() {
		return arrayTorta;
	}
	public void setArrayTorta(JRBeanCollectionDataSource arrayTorta) {
		this.arrayTorta = arrayTorta;
	}
	
	
	public static PuntoAEncuestar findMeById(Long id){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PuntoAEncuestar.class);
		criteria.add(Expression.eq(ID,id));
		PuntoAEncuestar punto= (PuntoAEncuestar)SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next();
		return punto;
		
	}
	
	public boolean equals(Object object) {
	
	 if(object instanceof PuntoAEncuestar)	{
			if (object == this)
			    return true;
			
			if(object != null && object instanceof ModelObject) {
				return this.getId().equals(((ModelObject) object).getId()) && 
				(this.getNombre() != null && this.getName().equalsIgnoreCase(((PuntoAEncuestar)object).getNombre()) && 
						this.getDescripcion() != null && this.getDescripcion().equalsIgnoreCase(((PuntoAEncuestar)object).getDescripcion()));
			}
			return false;
		
		
	 	}else{
	 			return super.equals(object);
	 	}}
}
