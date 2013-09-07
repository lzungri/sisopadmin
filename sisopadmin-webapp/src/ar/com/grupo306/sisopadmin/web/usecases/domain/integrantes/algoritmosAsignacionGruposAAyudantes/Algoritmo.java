package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.mailSender.Mail;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.ModelObjectComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors.EncuestaParaGrupoFilter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors.EncuestadaFilter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public abstract class Algoritmo {

	private List<Ayudante> ayudantes; // contiene los ayudantes y coordinadores 
	private List<Grupo> gruposAInscribir; // contiene todos los grupos a inscribir (tanto con como sin ayudante)
	private List<Grupo> gruposAInscribirConAyudante; // contiene solamente los grupos a inscribir con ayudante
	
	public void ejecutarAsigacion(){
		confirmarGruposConConflicto();
		borrarGruposInvalidos();
		buscarAyudantes();
		buscarGruposAInscribir();
		buscarLegajosRepetidos();
		seleccionarGruposAInscribirConAyudante();
		validarMaximaCantidadGruposCorregibleTotal();
		inscribirConAyudante(gruposAInscribirConAyudante, ayudantes);
		finalizarAsignacion();
	}
	
	private void confirmarGruposConConflicto(){
		SisopAdminServiceProvider.getPersistenceService().createQuery(
				"update Grupo g " +
				"set estado = :estadoConfirmadoManual, motivoConflicto = null " +
				"where estado = :estadoConConflicto")
				.setParameter("estadoConfirmadoManual", EstadoGrupo.CONFIRMADO_MANUAL)
				.setParameter("estadoConConflicto", EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION)
				.executeUpdate();
	}
	
	private void borrarGruposInvalidos(){
		SisopAdminServiceProvider.getPersistenceService().createQuery(
				"delete Alumno a " +
				"where a.grupo in " +
					"(from Grupo g " +
					"where g.estado not in (:estadoConfirmadoManual, :estadoInscripto, :estadoConConflicto))" +
				")")
				.setParameter("estadoConfirmadoManual", EstadoGrupo.CONFIRMADO_MANUAL)
				.setParameter("estadoInscripto", EstadoGrupo.INSCRIPTO)
				.setParameter("estadoConConflicto", EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION)
				.executeUpdate();

		List<Grupo> gruposInvalidos = SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Grupo grupo " +
				"where grupo.estado not in (:estadoConfirmadoManual, :estadoInscripto, :estadoConConflicto) ")
				.setParameter("estadoConfirmadoManual", EstadoGrupo.CONFIRMADO_MANUAL)
				.setParameter("estadoInscripto", EstadoGrupo.INSCRIPTO)
				.setParameter("estadoConConflicto", EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION)
				.list();

		FileWriter writer = null;
		try {

			writer = new FileWriter(SisopAdminConfig.getInstance().getProperty(Module.INTEGRANTES, "archivo.grupos.rechazados"));
			
			for (Grupo grupoInvalido : gruposInvalidos){
				grupoInvalido.setRoles(CollectionFactory.createSet());
				grupoInvalido.update();
				writer.write(grupoInvalido.getLoginName() + "\r\n");
				grupoInvalido.remove();
			}
			
		} catch(IOException e){
			throw new BusinessException("El archivo con los grupos rechazados no pudo ser generado");
		} finally {			
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e){
					throw new BusinessException("El archivo con los grupos rechazados no pudo ser cerrado");
				}
			}			
		}

	}
	
	private void buscarAyudantes(){
		ayudantes = SisopAdminServiceProvider.getPersistenceService().createCriteria(Ayudante.class).list();
	}
	
	private void buscarGruposAInscribir(){
		Long[] estados = {EstadoGrupo.CONFIRMADO_MANUAL, EstadoGrupo.INSCRIPTO};
		gruposAInscribir = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class)
				.add( Restrictions.in("estado", estados) )
				.list();
	}
	
	private void buscarLegajosRepetidos(){
		Map<String, Alumno> alumnos = new HashMap<String, Alumno>();
		for (Grupo grupo : gruposAInscribir){

			for (Alumno alumno : grupo.getAlumnos()){
				if (!alumnos.containsKey(alumno.getLegajo())){
					alumnos.put(alumno.getLegajo(), alumno);
				} else {
					Alumno alumnoEnMap = alumnos.get(alumno.getLegajo());
					
					if (!EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION.equals(alumno.getGrupo().getEstado()) &&
						!EstadoGrupo.INSCRIPTO.equals(alumno.getGrupo().getEstado())) {
						
						alumno.getGrupo().setEstado(EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION);						
						
						String motivoConflictoAnterior = alumno.getGrupo().getMotivoConflicto();
						if (motivoConflictoAnterior != null){
							alumno.getGrupo().setMotivoConflicto(motivoConflictoAnterior + "El legajo " + alumno.getLegajo() + " también aparece en el grupo " + alumnoEnMap.getGrupo().getNombre() + ". ");
						} else {
							alumno.getGrupo().setMotivoConflicto("El legajo " + alumno.getLegajo() + " también aparece en el grupo " + alumnoEnMap.getGrupo().getNombre() + ". ");
						}
						
						alumno.getGrupo().update();

						mandarMailConConflicto(alumno.getGrupo());
					}
									
					if (!EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION.equals(alumnoEnMap.getGrupo().getEstado()) &&
						!EstadoGrupo.INSCRIPTO.equals(alumnoEnMap.getGrupo().getEstado())){
						
						alumnoEnMap.getGrupo().setEstado(EstadoGrupo.CON_CONFLICO_DE_INSCRIPCION);

						String motivoConflictoAnterior = alumnoEnMap.getGrupo().getMotivoConflicto();
						if (motivoConflictoAnterior != null){
							alumnoEnMap.getGrupo().setMotivoConflicto(motivoConflictoAnterior + "El legajo " + alumnoEnMap.getLegajo() + " también aparece en el grupo " + alumno.getGrupo().getNombre() + ". ");
						} else {
							alumnoEnMap.getGrupo().setMotivoConflicto("El legajo " + alumnoEnMap.getLegajo() + " también aparece en el grupo " + alumno.getGrupo().getNombre() + ". ");
						}
						
						alumnoEnMap.getGrupo().update();

						mandarMailConConflicto(alumnoEnMap.getGrupo());
					}
				}				
			}
			
		}

		List<Grupo> gruposConfirmadosManual = new ArrayList<Grupo>();
		for (Grupo grupo : gruposAInscribir){
			if (EstadoGrupo.CONFIRMADO_MANUAL.equals(grupo.getEstado())){
				gruposConfirmadosManual.add(grupo);
			}
		}
		
		gruposAInscribir = gruposConfirmadosManual;	
	}
	
	private void seleccionarGruposAInscribirConAyudante(){
		gruposAInscribirConAyudante = new ArrayList<Grupo>();
		Byte cantidadMinIntegrantesParaTenerAyudante = new Byte(SisopAdminConfig.getInstance().getProperty(Module.INTEGRANTES, "cantidadMinIntegrantesParaTenerAyudante"));
		
		for (Grupo grupo : gruposAInscribir){			
			if (grupo.getAlumnos().size() >= cantidadMinIntegrantesParaTenerAyudante){
				gruposAInscribirConAyudante.add(grupo);
			}
		}	
	}
	
	private void validarMaximaCantidadGruposCorregibleTotal(){
		long maximaCantidadGruposCorregibleTotal = 0;
		for (Ayudante ayudante : ayudantes){
			maximaCantidadGruposCorregibleTotal = maximaCantidadGruposCorregibleTotal + ayudante.getMaximaCantidadGrupos() - ayudante.getGrupos().size();
		}
		
		if (maximaCantidadGruposCorregibleTotal < gruposAInscribirConAyudante.size()){
			throw new BusinessException("La cantidad total de grupos admitidos por los ayudantes no es suficiente. Es necesario poder corregir " + (gruposAInscribirConAyudante.size() - maximaCantidadGruposCorregibleTotal) + " grupo/s adicional/es.");
		}
	}
	
	protected abstract void inscribirConAyudante(List<Grupo> grupos, List<Ayudante> ayudantes);
	
	private void finalizarAsignacion(){
		FileWriter writer = null;
		try {
			writer = new FileWriter(SisopAdminConfig.getInstance().getProperty(Module.INTEGRANTES, "archivo.grupos.inscriptos"));
		
			Set<Rol> roles = new TreeSet<Rol>();
			roles.add(Rol.findMeByDomainCode(Rol.ROL_GRUPO_INSCRIPTO));
			
			for (Grupo grupo : gruposAInscribir){
				grupo.setEstado(EstadoGrupo.INSCRIPTO);
				grupo.setRoles(roles);
				PlantillaEncuesta encuestaFinder = new PlantillaEncuesta();
				Collection encuestas = encuestaFinder.findAll(PlantillaEncuesta.class);
				CollectionUtils.filter(encuestas,new EncuestadaFilter());
				CollectionUtils.filter(encuestas,new EncuestaParaGrupoFilter());
				Iterator iEncuestas = encuestas.iterator();
				while(iEncuestas.hasNext()){
					PlantillaEncuesta encuesta = (PlantillaEncuesta)iEncuestas.next();
					if(grupo.getEncuestasSinLlenar() == null)
						grupo.setEncuestasSinLlenar(CollectionFactory.createSet());
					grupo.getEncuestasSinLlenar().add(encuesta);
					if(encuesta.getUsuariosSinEncuestar() == null || encuesta.getUsuariosSinEncuestar().isEmpty())
						encuesta.setUsuariosSinEncuestar(CollectionFactory.createTreeSet(new ModelObjectComparator()));
					else{
						Set set = CollectionFactory.createTreeSet(new ModelObjectComparator());
						set.addAll(encuesta.getUsuariosSinEncuestar());
						encuesta.setUsuariosSinEncuestar(set);
					}	
					encuesta.getUsuariosSinEncuestar().add((Usuario)grupo);
					//encuesta.saveOrUpdate();
				}
				grupo.update();
				
				writer.write(grupo.getLoginName() + " " + grupo.getPassword() + "\r\n");
				
				mandarMailConfirmacion(grupo);
			}
			
		} catch(IOException e){
			throw new BusinessException("El archivo con los grupos inscriptos no pudo ser generado");
		} finally {			
			if (writer != null){
				try {
					writer.close();
				} catch (IOException e){
					throw new BusinessException("El archivo con los grupos inscriptos no pudo ser cerrado");
				}
			}			
		}

	}
	
	private void mandarMailConfirmacion(Grupo grupo){
		//Parametros, si estan vacios usa los del arch. de conf.
		String senderAddress="";
		String senderName="";
		
		String mailSubject="Sistemas Operativos: Grupo Inscripto";		
		ArrayList<String> destinatarios = new ArrayList<String>();		
		destinatarios.add(grupo.getEmail());
		
		Mail mail;
		if (grupo.getAyudante() != null){
			destinatarios.add(grupo.getAyudante().getEmail());
			mail = new Mail(senderAddress, senderName, mailSubject, "bodyTemplateInscripcionGrupoConAyudante");
			mail.addClavesRemplazo("ApellidoAyudante", grupo.getAyudante().getApellido());
			mail.addClavesRemplazo("NombreAyudante", grupo.getAyudante().getNombre());
			mail.addClavesRemplazo("EmailAyudante", grupo.getAyudante().getEmail());
		} else {
			mail = new Mail(senderAddress, senderName, mailSubject, "bodyTemplateInscripcionGrupoSinAyudante");			
		}
										
		mail.doSendMails(destinatarios, new ArrayList());
	}

	private void mandarMailConConflicto(Grupo grupo){
		//Parametros, si estan vacios usa los del arch. de conf.
		String senderAddress="";
		String senderName="";
		
		String mailSubject="Sistemas Operativos: Conflictos para inscribir al grupo";		
		ArrayList<String> destinatarios = new ArrayList<String>();		
		destinatarios.add(grupo.getEmail());
		
		Mail mail = new Mail(senderAddress, senderName, mailSubject, "bodyTemplateGrupoConConflictoDeInscripcion");			
		mail.doSendMails(destinatarios, new ArrayList());
	}
	
}
