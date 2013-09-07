package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.text.ParseException;

import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class InformeEvaluacionFaseTablePopulator extends TablePopulator {
	
	public void populateInformesEvaluacionFase() throws ParseException{
		createAndSaveInformeEvaluacionFase("Emulaso", 1, "pablo", "grupo1",
				"02092007", "03092007", "04092007", false, 80.0, 20.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 1, "pablo", "grupo1",
				"19092007", "20092007", "21092007", true, 80.0, 70.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 2, "pablo", "grupo1",
				"18092007", "19092007", "20092007", false, 50.0, 10.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 3, "pablo", "grupo1",
				"24092007", "25092007", "25092007", false, 70.0, 15.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 3, "pablo", "grupo1",
				"28092007", "29092007", "29092007", true, 60.0, 50.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 3, "pablo", "grupo1",
				"02102007", "03102007", "04102007", true, 40.0, 40.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 5, "pablo", "grupo1",
				"10112007", "11112007", "12112007", true, 90.0, 80.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 6, "pablo", "grupo1",
				"17112007", "18112007", "19112007", false, 100.0, 20.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 6, "pablo", "grupo1",
				"20112007", "21112007", "22112007", false, 100.0, 50.0);
		createAndSaveInformeEvaluacionFase("Emulaso", 6, "pablo", "grupo1",
				"25112007", "26112007", "27112007", true, 100.0, 95.0);
	}
	
	private void createAndSaveInformeEvaluacionFase(String nombreTP, Integer numeroFase,
			String loginNameAyudante, String loginNameGrupo, String fechaEntrega,
			String fechaAlta, String fechaEnvio, Boolean faseAprobada,
			Double puntajeEvaluado, Double puntajeObtenido) throws ParseException{
		InformeEvaluacionFase informe = new InformeEvaluacionFase();
		informe.setFase(Fase.findMeByNombreTPYNumeroFase(nombreTP, numeroFase));
		informe.setAyudanteEvaluador(Ayudante.findMeByLoginName(loginNameAyudante));
		informe.setGrupoEvaluado((Grupo)Usuario.findMeByLoginName(loginNameGrupo));
		informe.setEntregaEvaluada(findEntrega(loginNameGrupo, nombreTP, numeroFase, fechaEntrega));
		informe.setFechaAlta(DateUtils.getDateFromString(fechaAlta, "ddMMyyyy"));
		informe.setFechaEnvio(DateUtils.getDateFromString(fechaEnvio, "ddMMyyyy"));
		informe.setPathInforme("");
		informe.setFaseAprobada(faseAprobada);
		informe.setPorcentajeEvaluado(puntajeEvaluado);
		informe.setPorcentajeCumplimientoFase(puntajeObtenido);
		informe.setEstado(Estado.findMeByDomainCode(InformeEvaluacionFase.ESTADO_CODE_ENVIADA));
		informe.save();
	}
	
	private Entrega findEntrega(String loginNameGrupo, String nombreTP, Integer numeroFase, String fechaEntrega) throws ParseException {
		return (Entrega) SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Entrega entrega " +
				"where entrega.grupo.loginName = :loginNameGrupo " +
				"and entrega.fase.tp.nombre = :nombreTP " +
				"and entrega.fase.numero = :numeroFase " +
				"and entrega.fechaEntrega = :fechaEntrega ")
				.setParameter("loginNameGrupo", loginNameGrupo)
				.setParameter("nombreTP", nombreTP)
				.setParameter("numeroFase", numeroFase)
				.setParameter("fechaEntrega", DateUtils.getDateFromString(fechaEntrega, "ddMMyyyy"))				
				.uniqueResult();
	}

}
