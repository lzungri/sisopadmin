package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminReportBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;

/**
 * @author Pablo
 */
public class VisualizarAvanceUseCaseModel extends AdminReportBaseUseCaseModel {
	
	private Tp tp;
	private Grupo grupo;
	private Boolean grupoLogueado;
	
	@Secure ({})
	@Transactional
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
		String userLoginName = ((LoggedUserContext)context.getUserContext()).getUserLoginName();
		Usuario usuario = Usuario.findMeByLoginName(userLoginName);
		if (usuario instanceof Grupo){
			grupo = (Grupo) usuario;
			grupoLogueado = true;
		} else {
			grupoLogueado = false;
		}
		
	}
	
	@Transactional
	public void generateReport(UseCaseContext useCaseContext) {
		if (!seleccionValida()){
			useCaseContext.addErrorMessage("Falta ingregar el trabajo práctico o el grupo");
			useCaseContext.refreshUseCase();
			return;
		}
		
		setOutputFormat("pdf");
		super.generateReport(useCaseContext);
	}
	
	private Boolean seleccionValida(){
		return (tp != null && grupo != null);
	}
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Tp getTp() {
		return tp;
	}

	public void setTp(Tp tp) {
		this.tp = tp;
	}

	public void seleccionarTP(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionarTP");	
	}

	public void returnFromSeleccionarTP(UseCaseContext useCaseContext) {
		AdministrarTPsUseCaseModel model = (AdministrarTPsUseCaseModel) useCaseContext.getReturnedModel();
		tp = model != null ? (Tp) model.getSelectedEntities().iterator().next() : null;
	}
	
	public void seleccionarGrupo(UseCaseContext useCaseContext) {
		Map parameters = CollectionFactory.createMap();
		useCaseContext.goToChildUseCase(AdministrarGruposUseCase.class, new SelectionMode(), parameters, "returnFromSeleccionarGrupo");
	}
	
	public void returnFromSeleccionarGrupo(UseCaseContext useCaseContext) {
		AdministrarGruposUseCaseModel model = (AdministrarGruposUseCaseModel) useCaseContext.getReturnedModel();
		grupo = model != null ? (Grupo) model.getSelectedEntities().iterator().next() : null;
	}

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return null;
	}

	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		return null;
	}

	protected Class<? extends ModelObject> entityClass() {
		return null;
	}

	public JRDataSource createReportDatasource() {
		return new JREmptyDataSource();
	}
	
	public Map createReportParameters() {
		Map parameters = CollectionFactory.createMap();
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Integer cantidadFasesObligatorias = fillFases(dataset);
		
		List<CategoryTextAnnotation> anotaciones = new ArrayList<CategoryTextAnnotation>();
		Integer cantidadFasesObligatoriasAprobadas = fillDataset(dataset, anotaciones);
		
		JFreeChart chart = createChart(dataset, anotaciones);	
		BufferedImage image = chart.createBufferedImage(1000, 700);		
		parameters.put("informe", image);
		
		parameters.put("nombreTP", tp.getNombre());
		parameters.put("cantidadFasesObligatorias", cantidadFasesObligatorias);
		parameters.put("nombreGrupo", grupo.getNombre());
		parameters.put("cantidadFasesObligatoriasAprobadas", cantidadFasesObligatoriasAprobadas);
		
		return parameters;
	}

	private Integer fillFases(DefaultCategoryDataset dataset){
		List<Fase> fases = SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Fase fase " +
				"where fase.tp = :tp " +
				"order by fase.numero asc")
				.setParameter("tp", tp)
				.list();
		
		Integer cantidadFasesObligatorias = 0;
		for (Fase fase : fases){
			dataset.addValue(null, "series", fase.getNombre());
			if (fase.isEntregaObligatoria()){
				cantidadFasesObligatorias++;
			}
		}
		
		return cantidadFasesObligatorias;
	}
	
	private Integer fillDataset(DefaultCategoryDataset dataset, List<CategoryTextAnnotation> anotaciones){
		List<InformeEvaluacionFase> informes =
				SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from InformeEvaluacionFase informeEvaluacionFase " +
				"where informeEvaluacionFase.fase.tp = :tp " +
				"and informeEvaluacionFase.grupoEvaluado = :grupo " +
				"and informeEvaluacionFase.faseAprobada = :faseAprobada " +
				"and informeEvaluacionFase.estado = :estadoEnviada " +
				"order by informeEvaluacionFase.entregaEvaluada.fechaEntrega asc")
				.setParameter("tp", tp)
				.setParameter("grupo", grupo)
				.setParameter("faseAprobada", Boolean.TRUE)
				.setParameter("estadoEnviada", Estado.findMeByDomainCode(InformeEvaluacionFase.ESTADO_CODE_ENVIADA))
				.list();
		
		Font annotationFont = CategoryTextAnnotation.DEFAULT_FONT.deriveFont((float)15.0);

		Integer cantidadFasesObligatoriasAprobadas = 0;
		
		// Solo se cargan en el grafico la primer entrega aprobada de cada fase
		for (InformeEvaluacionFase informe : informes){
			Double puntaje = informe.getPorcentajeCumplimientoFase();
			if (dataset.getValue("series", informe.getFase().getNombre()) == null){									
				dataset.addValue(puntaje, "series", informe.getFase().getNombre());
				
				String retraso;
				if (informe.getFase().getFechaFin().after(informe.getEntregaEvaluada().getFechaEntrega())){
					retraso = "Sin retraso";
				} else {
					retraso = "Retraso: " + DateUtils.subtract(informe.getFase().getFechaFin(), informe.getEntregaEvaluada().getFechaEntrega()) + " días";
				}
				CategoryTextAnnotation retrasoAnnotation = new CategoryTextAnnotation(retraso, informe.getFase().getNombre(), puntaje - 2); 
				retrasoAnnotation.setFont(annotationFont);
				anotaciones.add(retrasoAnnotation);				
				
				if (informe.getFase().isEntregaObligatoria()){
					CategoryTextAnnotation entregaObligatoriaAnnotation = new CategoryTextAnnotation("Entrega obligatoria", informe.getFase().getNombre(), puntaje - 5);
					entregaObligatoriaAnnotation.setFont(annotationFont);
					anotaciones.add(entregaObligatoriaAnnotation);
					
					cantidadFasesObligatoriasAprobadas++;
				}				
				
			}
		}
		
		return cantidadFasesObligatoriasAprobadas;
	}
	
	private JFreeChart createChart(DefaultCategoryDataset dataset, List<CategoryTextAnnotation> anotaciones){	
		JFreeChart chart = ChartFactory.createLineChart(
				"Informe de avance de grupo", 
				"Fases", 
				"Porcentaje de aprobación", 
				dataset, 
				PlotOrientation.VERTICAL, 
				false, 
				false, 
				false);		
			
		DefaultCategoryItemRenderer renderer = new DefaultCategoryItemRenderer();
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesLinesVisible(0, false);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setRenderer(renderer);
		plot.getRangeAxis().setUpperBound(100);
		plot.getDomainAxis().setTickLabelFont( plot.getDomainAxis().getTickLabelFont().deriveFont((float)15.0) );
		plot.getDomainAxis().setMaximumCategoryLabelLines(3);
		
		for (CategoryTextAnnotation anotacion : anotaciones){
			plot.addAnnotation(anotacion);
		}

		return chart;
	}

	
	public String getReportName() {
		return "informeAvanceGrupo";
	}

	public Boolean getGrupoLogueado() {
		return grupoLogueado;
	}

	public void setGrupoLogueado(Boolean grupoLogueado) {
		this.grupoLogueado = grupoLogueado;
	}

}