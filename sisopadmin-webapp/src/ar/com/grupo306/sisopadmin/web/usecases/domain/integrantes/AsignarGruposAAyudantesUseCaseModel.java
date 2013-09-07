package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes.Algoritmo;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * @author Pablo
 */
public class AsignarGruposAAyudantesUseCaseModel extends BaseUseCaseModel {
	
	private String algoritmo;
	
	private List<Usuario> ayudantesDisponibles;
	private List<Grupo> gruposAAsignar;
	private List<Grupo> gruposAsignados;
	private List<Grupo> gruposAsignadosAnteriormente;
	private List<Grupo> gruposConConflicto;
	
	public List<Grupo> getGruposAAsignar() {
		return gruposAAsignar;
	}

	public void setGruposAAsignar(List<Grupo> gruposAAsignar) {
		this.gruposAAsignar = gruposAAsignar;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	
	public Integer getGruposConConflictoSize(){
		return gruposConConflicto.size();
	}
	
	public Integer getGruposAsignadosSize(){
		return gruposAsignados.size();
	}


	@Secure ({})
	@Description ("Acceder")
	@Transactional
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
		cargarListas(false);
	}
	
	private void cargarListas(Boolean ejecucionAsignacion){
		if(ejecucionAsignacion){
			gruposAsignadosAnteriormente.addAll(gruposAsignados);
			List<Long> gruposAsignadosAnteriormenteIds = new ArrayList<Long>();
			for (Grupo grupo : gruposAsignadosAnteriormente){
				gruposAsignadosAnteriormenteIds.add(grupo.getId());
			}			
			
			gruposAsignados = SisopAdminServiceProvider.getPersistenceService()
					.createCriteria(Grupo.class)
					.add( Restrictions.eq("estado", EstadoGrupo.INSCRIPTO) )
					.add( Restrictions.not( Restrictions.in("id", gruposAsignadosAnteriormenteIds)) )
					.list();			
		} else {
			gruposAsignadosAnteriormente = new ArrayList<Grupo>();
			gruposAsignados = SisopAdminServiceProvider.getPersistenceService()
					.createCriteria(Grupo.class)
					.add( Restrictions.eq("estado", EstadoGrupo.INSCRIPTO) )
					.list();
		}
		
		ayudantesDisponibles = SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Ayudante ayudante " +
				"where ayudante.grupos.size < ayudante.maximaCantidadGrupos")
				.list();
		gruposAAsignar = SisopAdminServiceProvider.getPersistenceService()
				.createCriteria(Grupo.class)
				.add( Restrictions.eq("estado", EstadoGrupo.CONFIRMADO_MANUAL) )
				.list();
		gruposConConflicto = SisopAdminServiceProvider.getPersistenceService()
				.createCriteria(Grupo.class)
				.add( Restrictions.eq("estado", EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION) )
				.list();
	}

	@Transactional
	public void asignar(UseCaseContext context) {
		try {
			Class claseAlgoritmo = Class.forName(algoritmo);
			Algoritmo algoritmoAsignacion = (Algoritmo) claseAlgoritmo.newInstance();
			algoritmoAsignacion.ejecutarAsigacion();
			cargarListas(true);
			context.addMessage("Asignación de grupos a ayudantes realizada exitosamente.");
		} catch (ClassNotFoundException e) {
			context.addErrorMessage("Ha ocurrido un error: No se ha podido realizar la asignación.");
		} catch (InstantiationException e){
			context.addErrorMessage("Ha ocurrido un error: No se ha podido realizar la asignación.");
		} catch (IllegalAccessException e){
			context.addErrorMessage("Ha ocurrido un error: No se ha podido realizar la asignación.");
		}
	}

	public List<Usuario> getAyudantesDisponibles() {
		return ayudantesDisponibles;
	}

	public void setAyudantesDisponibles(List<Usuario> ayudantesDisponibles) {
		this.ayudantesDisponibles = ayudantesDisponibles;
	}

	public List<Grupo> getGruposAsignados() {
		return gruposAsignados;
	}

	public void setGruposAsignados(List<Grupo> gruposAsignados) {
		this.gruposAsignados = gruposAsignados;
	}

	public List<Grupo> getGruposConConflicto() {
		return gruposConConflicto;
	}

	public void setGruposConConflicto(List<Grupo> gruposConConflicto) {
		this.gruposConConflicto = gruposConConflicto;
	}

	public List<Grupo> getGruposAsignadosAnteriormente() {
		return gruposAsignadosAnteriormente;
	}

	public void setGruposAsignadosAnteriormente(
			List<Grupo> gruposAsignadosAnteriormente) {
		this.gruposAsignadosAnteriormente = gruposAsignadosAnteriormente;
	}

	public Integer getGruposAsignadosAnteriormenteSize() {
		return gruposAsignadosAnteriormente.size();
	}

}