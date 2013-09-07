package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.PuntoEncuestadoComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.AsignadorDeEncuestas;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;



public class PlantillaEncuestaTablePopulator extends TablePopulator{
	
	
	public void populatePlantillaEncuestaEnEncuesta(){
		Usuario usuario = Usuario.findMeByLoginName("soledad");
		PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
		plantilla.setNombre("Encuesta sobre performance de los ayudantes");
		plantilla.setFechaAlta(DateUtils.addDays(new Date(), -300));
		plantilla.setFechaFin(DateUtils.addDays(new Date(), 300));
		plantilla.setUsuario(usuario);
		PuntoAEncuestar pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("El ayudante mostró conocimiento ante las dudas del grupo");
		pEncuestar.setNombre("Conocimiento del ayudante");
		pEncuestar.setNumero(new Long(1));
		plantilla.setPuntosAEncuestar(CollectionFactory.createSet());
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("Las informaciones y avisos de los ayudantes concidió con los parámetros generales de la cátedra");
		pEncuestar.setNombre("Alineamiento del ayudante");
		pEncuestar.setNumero(new Long(2));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		
		
		plantilla.setDestinatario("GRUPO");
		Iterator iPuntosAEncuestar = plantilla.getPuntosAEncuestar().iterator();
		plantilla.setEstado(new Long(1));
		
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
			punto.setPlantillaEncuesta(plantilla);
			punto.saveOrUpdate();
		}
		plantilla.saveOrUpdate();
		AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance("GRUPO");
		asignador.asignar(plantilla);
		
		/** LOS GRUPOS LLENAN LA ENCUESTA*/
		Collection grupos = new Usuario().findAll(Grupo.class);
		Iterator iGrupos = grupos.iterator();
		int i = 4;
		while(iGrupos.hasNext()){
			Grupo grupo = (Grupo)iGrupos.next();
			PlantillaEncuestada encuestada = new PlantillaEncuestada();
			encuestada.setDestinatario(plantilla.getDestinatario());
			encuestada.setNombre(plantilla.getNombre());
			encuestada.setEstado(plantilla.getEstado());
			encuestada.setFechaAlta(plantilla.getFechaAlta());
			encuestada.setFechaFin(plantilla.getFechaFin());
			encuestada.setObligatoriedad(encuestada.getObligatoriedad());
			Collection puntosAEncuestar = plantilla.getPuntosAEncuestar();
			Iterator iPuntos = puntosAEncuestar.iterator();
			Set setEncuestados = new TreeSet(new PuntoEncuestadoComparator());
			Map map = CollectionFactory.createMap();
			while(iPuntos.hasNext()){
				PuntoAEncuestar punto = (PuntoAEncuestar)iPuntos.next();
				PuntoEncuestado encuestado = new PuntoEncuestado();
				encuestado.setPuntuacion(new Long(5));
				encuestado.setPuntoAEncuestar(punto);
				setEncuestados.add(encuestado);
				map.put(punto, encuestado);
			}
			encuestada.setPuntosEncuestados(setEncuestados);
			usuario.getEncuestasSinLlenar().remove(plantilla);
			usuario.saveOrUpdate();
			if(plantilla.getPlantillasEncuestadas() == null)
				plantilla.setPlantillasEncuestadas(CollectionFactory.createSet());
			plantilla.getPlantillasEncuestadas().add(encuestada);
			encuestada.setPlantillaEncuesta(plantilla);
			Iterator iMap = map.entrySet().iterator();
			
			while(iMap.hasNext()){
				Entry entry = (Entry)iMap.next();
				PuntoEncuestado punto = (PuntoEncuestado)entry.getValue();
				PuntoAEncuestar aEncuestar = (PuntoAEncuestar)entry.getKey();
				
				punto.setPuntuacion(new Long(i));
				if( i == 10)
					i = 0;
				i = i + 2 ;
				if(aEncuestar.getPuntosEncuestados()== null || aEncuestar.getPuntosEncuestados().isEmpty())
					aEncuestar.setPuntosEncuestados(CollectionFactory.createSet());
				aEncuestar.getPuntosEncuestados().add(punto);
				punto.setPuntoAEncuestar(aEncuestar);
				punto.setPlantillaEncuestada(encuestada);
				punto.saveOrUpdate();
				
			}
			
			//plantilla.getUsuariosSinEncuestar().remove(usuario);
			//plantilla.saveOrUpdate();
			encuestada.saveOrUpdate();
			
			
		
		}
		

	}
	
	
	public void populatePlantillaEncuestaDeLaCatedra(){
		Usuario usuario = Usuario.findMeByLoginName("soledad");
		PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
		plantilla.setNombre("Encuesta sobre trabajo práctico cuatrimestral");
		plantilla.setFechaAlta(DateUtils.addDays(new Date(), -300));
		plantilla.setFechaFin(DateUtils.addDays(new Date(), 300));
		plantilla.setUsuario(usuario);
		PuntoAEncuestar pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Qué tan claro era el enunciado del TP?");
		pEncuestar.setNombre("Enunciado del TP");
		pEncuestar.setNumero(new Long(1));
		plantilla.setPuntosAEncuestar(CollectionFactory.createSet());
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Qué tan adecuada fue la dificultad del TP?");
		pEncuestar.setNombre("Alineamiento del ayudante");
		pEncuestar.setNumero(new Long(2));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Cómo considera la magnitud del TP?");
		pEncuestar.setNombre("Magnitud del TP");
		pEncuestar.setNumero(new Long(3));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Cómo le pareció la relación del TP con la materia?");
		pEncuestar.setNombre("Relación TP-Materia");
		pEncuestar.setNumero(new Long(4));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Qué grado de apreciación tiene el grupo con respecto al TP?");
		pEncuestar.setNombre("Apreciación del TP");
		pEncuestar.setNumero(new Long(5));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("Indicar la disponibilidad del ayudante para aclarar dudas");
		pEncuestar.setNombre("Disponibilidad del ayudante");
		pEncuestar.setNumero(new Long(6));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Las respuestas del ayudante fueron útiles para la solución de los problemas que se les presentaron?");
		pEncuestar.setNombre("Respuestas del ayudante");
		pEncuestar.setNumero(new Long(7));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("Puntuar el tiempo de respuesta del ayudante");
		pEncuestar.setNombre("Tiempo de respuesta");
		pEncuestar.setNumero(new Long(8));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("Puntuar la calidad del feedback que proporcionó el ayudante");
		pEncuestar.setNombre("Feedback");
		pEncuestar.setNumero(new Long(9));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("¿Cómo le pareció la entrega final?");
		pEncuestar.setNombre("Entrega final");
		pEncuestar.setNumero(new Long(10));
		plantilla.getPuntosAEncuestar().add(pEncuestar);
		
		
		plantilla.setDestinatario("GRUPO");
		Iterator iPuntosAEncuestar = plantilla.getPuntosAEncuestar().iterator();
		plantilla.setEstado(new Long(1));
		
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
			punto.setPlantillaEncuesta(plantilla);
			punto.saveOrUpdate();
		}
		plantilla.saveOrUpdate();
		AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance("GRUPO");
		asignador.asignar(plantilla);
		
		/** LOS GRUPOS LLENAN LA ENCUESTA*/
		Collection grupos = new Usuario().findAll(Grupo.class);
		Iterator iGrupos = grupos.iterator();
		int i = 4;
		int cargadas = 0;
		while(cargadas < 10){
			
			PlantillaEncuestada encuestada = new PlantillaEncuestada();
			encuestada.setDestinatario(plantilla.getDestinatario());
			encuestada.setNombre(plantilla.getNombre());
			encuestada.setEstado(plantilla.getEstado());
			encuestada.setFechaAlta(plantilla.getFechaAlta());
			encuestada.setFechaFin(plantilla.getFechaFin());
			encuestada.setObligatoriedad(encuestada.getObligatoriedad());
			Collection puntosAEncuestar = plantilla.getPuntosAEncuestar();
			Iterator iPuntos = puntosAEncuestar.iterator();
			Set setEncuestados = new TreeSet(new PuntoEncuestadoComparator());
			Map map = CollectionFactory.createMap();
			while(iPuntos.hasNext()){
				PuntoAEncuestar punto = (PuntoAEncuestar)iPuntos.next();
				PuntoEncuestado encuestado = new PuntoEncuestado();
				encuestado.setPuntuacion(new Long(5));
				encuestado.setPuntoAEncuestar(punto);
				setEncuestados.add(encuestado);
				map.put(punto, encuestado);
			}
			encuestada.setPuntosEncuestados(setEncuestados);
			usuario.getEncuestasSinLlenar().remove(plantilla);
			usuario.saveOrUpdate();
			if(plantilla.getPlantillasEncuestadas() == null)
				plantilla.setPlantillasEncuestadas(CollectionFactory.createSet());
			plantilla.getPlantillasEncuestadas().add(encuestada);
			encuestada.setPlantillaEncuesta(plantilla);
			Iterator iMap = map.entrySet().iterator();
			
			while(iMap.hasNext()){
				Entry entry = (Entry)iMap.next();
				PuntoEncuestado punto = (PuntoEncuestado)entry.getValue();
				PuntoAEncuestar aEncuestar = (PuntoAEncuestar)entry.getKey();
				
				punto.setPuntuacion(new Long(i));
				if( i >= 10)
					i = 0;
				i = i + new BigDecimal(Math.random()*33333).intValue() % 10 ;
				if(aEncuestar.getPuntosEncuestados()== null || aEncuestar.getPuntosEncuestados().isEmpty())
					aEncuestar.setPuntosEncuestados(CollectionFactory.createSet());
				aEncuestar.getPuntosEncuestados().add(punto);
				punto.setPuntoAEncuestar(aEncuestar);
				punto.setPlantillaEncuestada(encuestada);
				punto.saveOrUpdate();
				
			}
			
			//plantilla.getUsuariosSinEncuestar().remove(usuario);
			//plantilla.saveOrUpdate();
			encuestada.saveOrUpdate();
			cargadas++;
			
		
		}
		

	}
	
	public void populatePlantillaEncuestaFutura(){
		Usuario usuario = Usuario.findMeByLoginName("soledad");
		PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
		plantilla.setNombre("Encuesta sobre performance de los grupos");
		plantilla.setFechaAlta(DateUtils.addDays(new Date(), 10));
		plantilla.setFechaFin(DateUtils.addDays(new Date(), 300));
		plantilla.setUsuario(usuario);
		Collection puntos = CollectionFactory.createSet();
		PuntoAEncuestar pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("El grupo mostró responsabilidad y entusiasmo en la materia");
		pEncuestar.setNombre("Performance grupo");
		pEncuestar.setNumero(new Long(1));
		puntos.add(pEncuestar);
		pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("Las consultas de los grupos mostró conocimiento e incremento en el mismo a lo largo de la cursada");
		pEncuestar.setNombre("Conocimiento del grupo");
		pEncuestar.setNumero(new Long(1));
		puntos.add(pEncuestar);
		
		plantilla.setPuntosAEncuestar(puntos);
		plantilla.setDestinatario("AYUDANTE");
		Iterator iPuntosAEncuestar = puntos.iterator();
		plantilla.setEstado(new Long(1));
		
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
			punto.setPlantillaEncuesta(plantilla);
			punto.saveOrUpdate();
		}
		plantilla.saveOrUpdate();
		AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance("AYUDANTE");
		asignador.asignar(plantilla);
	}
	
	public void populatePlantillaEncuestaPasada(){
		Usuario usuario = Usuario.findMeByLoginName("soledad");
		PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
		plantilla.setNombre("Asignador de grupos");
		plantilla.setFechaAlta(DateUtils.addDays(new Date(), -10));
		plantilla.setFechaFin(DateUtils.addDays(new Date(), -2));
		plantilla.setUsuario(usuario);
		Collection puntos = CollectionFactory.createSet();
		PuntoAEncuestar pEncuestar = new PuntoAEncuestar();
		pEncuestar.setDescripcion("El sistema le ocasionó problemas al definirle al grupo un ayudante");
		pEncuestar.setNombre("Asignacion de ayudante");
		pEncuestar.setNumero(new Long(1));
		puntos.add(pEncuestar);
		
		
		plantilla.setPuntosAEncuestar(puntos);
		plantilla.setDestinatario("GRUPO");
		Iterator iPuntosAEncuestar = puntos.iterator();
		plantilla.setEstado(new Long(1));
		
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
			punto.setPlantillaEncuesta(plantilla);
			punto.saveOrUpdate();
		}
		plantilla.saveOrUpdate();
		AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance("GRUPO");
		asignador.asignar(plantilla);
		/** LOS GRUPOS LLENAN LA ENCUESTA*/
		Collection grupos = new Usuario().findAll(Grupo.class);
		Iterator iGrupos = grupos.iterator();
		while(iGrupos.hasNext()){
			Grupo grupo = (Grupo)iGrupos.next();
			PlantillaEncuestada encuestada = new PlantillaEncuestada();
			encuestada.setDestinatario(plantilla.getDestinatario());
			encuestada.setNombre(plantilla.getNombre());
			encuestada.setEstado(plantilla.getEstado());
			encuestada.setFechaAlta(plantilla.getFechaAlta());
			encuestada.setFechaFin(plantilla.getFechaFin());
			encuestada.setObligatoriedad(encuestada.getObligatoriedad());
			Collection puntosAEncuestar = plantilla.getPuntosAEncuestar();
			Iterator iPuntos = puntosAEncuestar.iterator();
			Set setEncuestados = new TreeSet(new PuntoEncuestadoComparator());
			Map map = CollectionFactory.createMap();
			while(iPuntos.hasNext()){
				PuntoAEncuestar punto = (PuntoAEncuestar)iPuntos.next();
				PuntoEncuestado encuestado = new PuntoEncuestado();
				encuestado.setPuntuacion(new Long(5));
				encuestado.setPuntoAEncuestar(punto);
				setEncuestados.add(encuestado);
				map.put(punto, encuestado);
			}
			encuestada.setPuntosEncuestados(setEncuestados);
			usuario.getEncuestasSinLlenar().remove(plantilla);
			usuario.saveOrUpdate();
			if(plantilla.getPlantillasEncuestadas() == null)
				plantilla.setPlantillasEncuestadas(CollectionFactory.createSet(PlantillaEncuestada.class));
			plantilla.getPlantillasEncuestadas().add(encuestada);
			encuestada.setPlantillaEncuesta(plantilla);
			Iterator iMap = map.entrySet().iterator();
			while(iMap.hasNext()){
				Entry entry = (Entry)iMap.next();
				PuntoEncuestado punto = (PuntoEncuestado)entry.getValue();
				PuntoAEncuestar aEncuestar = (PuntoAEncuestar)entry.getKey();
				int i = 4;
				punto.setPuntuacion(new Long(i));
				if( i == 10)
					i = 0;
				i = i + 2 ;
				if(aEncuestar.getPuntosEncuestados()== null || aEncuestar.getPuntosEncuestados().isEmpty())
					aEncuestar.setPuntosEncuestados(CollectionFactory.createSet());
				aEncuestar.getPuntosEncuestados().add(punto);
				punto.setPuntoAEncuestar(aEncuestar);
				punto.setPlantillaEncuestada(encuestada);
				punto.saveOrUpdate();
				
			}
			
			//plantilla.getUsuariosSinEncuestar().remove(usuario);
			//plantilla.saveOrUpdate();
			encuestada.saveOrUpdate();
		}
	}

}
