package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.functors.EncuestasGruposPredicate;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para quitar el atributo nombre, que pasa a ser del Usuario 
 */

public class Grupo extends Usuario{	
	Long estado;	
	Set<Alumno> alumnos;
	Ayudante ayudante;
	String motivoConflicto;
	
	public String getMotivoConflicto() {
		return motivoConflicto;
	}
	public void setMotivoConflicto(String motivoConflicto) {
		this.motivoConflicto = motivoConflicto;
	}
	public Ayudante getAyudante() {
		return ayudante;
	}
	public void setAyudante(Ayudante ayudante) {
		this.ayudante = ayudante;
	}
	public Set<Alumno> getAlumnos() {
		if(alumnos == null)
			alumnos = CollectionFactory.createSet(Alumno.class);
		return alumnos;
	}
	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}	
	
	public void initialize(){
		Collection plantillas = new PlantillaEncuesta().findAll(PlantillaEncuesta.class);
		plantillas = CollectionUtils.select(plantillas,new EncuestasGruposPredicate());
		this.setEncuestasSinLlenar(plantillas);
	}
	
	public static Grupo findMeByName(String name){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class);
		criteria.add(Expression.eq("nombre", name));
		try{
			return (Grupo)(SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next());
		}catch(Exception e){
			return null;
		}
	}
	
	public static List findByName(String name){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class);
		criteria.add(Expression.like("nombre", "%" + name + "%"));
		return (SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));		
	}
	
	public static List findInscriptosSinAyudante(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class);
		criteria.add(Expression.isNull("ayudante"));
		criteria.add(Expression.eq("estado", 5L));
		return (SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));		
	}
	
	
	public static List findAll(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class);
		return (SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria));
	}
	
	public List<Usuario> getPosiblesReceptoresAyudantesDeMensaje(){		
		List<Usuario> posiblesReceptoresAyudantesDeMensaje;
		if (ayudante != null && !(ayudante instanceof Coordinador)){			
			ayudante.getNombre(); // Hago esto porque sino tira LazyInitializationException
			posiblesReceptoresAyudantesDeMensaje = new ArrayList<Usuario>();
			posiblesReceptoresAyudantesDeMensaje.add(ayudante);
		} else {			
			// ningún ayudante
			posiblesReceptoresAyudantesDeMensaje = super.getPosiblesReceptoresAyudantesDeMensaje();			
		}
		return posiblesReceptoresAyudantesDeMensaje;		
	}
	
	public List<Usuario> getPosiblesReceptoresCoordinadoresDeMensaje(){		
		List<Usuario> posiblesReceptoresCoordinadoresDeMensaje;
		if (ayudante != null){
			
			posiblesReceptoresCoordinadoresDeMensaje = new ArrayList<Usuario>();
			Usuario coordinador = ayudante.getCoordinador();
			if (coordinador != null){
				coordinador.getNombre(); // Hago esto porque sino tira LazyInitializationException
				posiblesReceptoresCoordinadoresDeMensaje.add(coordinador);
			} else {
				
				if (ayudante instanceof Coordinador){
					// el ayudante es un coordinador
					ayudante.getNombre(); // Hago esto porque sino tira LazyInitializationException
					posiblesReceptoresCoordinadoresDeMensaje.add(ayudante);					
				} else {
					// el ayudante no tiene coordinador, se devuelven todos los coordinadores
					posiblesReceptoresCoordinadoresDeMensaje = SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class).list();
				}
			}
			
		} else {			
			// todos los coordinadores
			posiblesReceptoresCoordinadoresDeMensaje = SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class).list();			
		}
		return posiblesReceptoresCoordinadoresDeMensaje;		
	}

	public void remove() {
		
		//Borrar las entregas de un grupo antes de eliminarlo
		for(Iterator it = Entrega.findBy(this.getId(), null).iterator();it.hasNext();){
			((Entrega)it.next()).remove();
		}		
		//Borrar los informe evaluación fase de un grupo antes de eliminarlo
		for(Iterator it = InformeEvaluacionFase.findBy(null, null, this.getId()).iterator();it.hasNext();){
			((InformeEvaluacionFase)it.next()).remove();
		}
		//Borrar las evaluacionesFasePorPlantilla de un grupo antes de eliminarlo
		for(Iterator it = EvaluacionFasePorPlantilla.findBy(null, null, null, null, this.getId()).iterator();it.hasNext();){
			((EvaluacionFasePorPlantilla)it.next()).remove();
		}		
		
		if(getAyudante() != null) {
			getAyudante().getGrupos().remove(this);
			getAyudante().saveOrUpdate();
		}
		setAyudante(null);
		saveOrUpdate();
		
		super.remove();
	}
}