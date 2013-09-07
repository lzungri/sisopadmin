package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;


import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.CollectionUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.ModelObjectComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.PuntoEncuestadoComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.comparator.NombreComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors.EncuestadaFilter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.RolDomainCodes;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminReportBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;

import com.sun.image.codec.jpeg.JPEGEncodeParam;

public class AdministrarEncuestaUseCaseModel extends AdminReportBaseUseCaseModel {
	
	// Búsqueda de la encuesta
	private String nombrePlantillaBusqueda = "";
	private Date fechaInicioBusqueda = null;
	private Date fechaFinBusqueda = null;
	private Collection encuestas = null;
	private JPEGEncodeParam image;
	private Boolean saved;
	private String tipoBuscar;
	private boolean isResultVisible = false;
	//****************
	private String pdfPath;
	
	static String OPERACION = "operacion";
	static String ALTA = "ALTA";
	static String ELIMINAR = "ELIMINAR";
	static String MODIFICAR = "MODIFICAR";
	static String LLENAR = "LLENAR";
	static String VISUALIZAR_RESULTADO = "VISUALIZAR_RESULTADO";
	
	

	@Secure ({OperationMode.class})
	@Description ("Administrar encuestas")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		this.setMode(new OperationMode());
		
		Usuario usuario;
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			context.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
		}
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
		Collection roles = usuario.getRoles();
		Iterator iRoles = roles.iterator();
		boolean admin = false;
		while(iRoles.hasNext()){
			Rol rol = (Rol)iRoles.next();
			if(RolDomainCodes.ROL_COORDINADOR.equalsIgnoreCase(rol.getDomainCode()) || RolDomainCodes.ROL_ADMIN.equalsIgnoreCase(rol.getDomainCode()))
				admin = true;
			
		}
		if(admin)
			this.buscarEncuesta(context);
		else
			this.buscarEncuestaLlenado(context);
		isResultVisible = false;

	}
	/**
	 * Busca las encuestas del sistema ubicadas en el rango indicado y con el nombre seleccionado.
	 * @param context
	 */
	@Transactional
	public void buscarEncuesta(UseCaseContext context){
		Date fechaInicio= null;
		Date fechaFin = null; 
		try{
			if (fechaInicioBusqueda != null)
				fechaInicio= fechaInicioBusqueda;
			if (fechaFinBusqueda != null)
				fechaFin=fechaFinBusqueda;
		}catch(Exception e){}
		TreeSet set = CollectionFactory.createTreeSet(new ModelObjectComparator());
		Collection encuestadas = PlantillaEncuesta.findUs(nombrePlantillaBusqueda,fechaInicio,fechaFin);
		CollectionUtils.filter(encuestadas,new EncuestadaFilter());
		set.addAll(encuestadas);
		
		encuestas = set;
		if(encuestas== null || encuestas.isEmpty())
			context.addMessage("No existen encuestas con los parámetros seleccionados");
		context.refreshUseCase();
		
	}
	
	/**
	 * Busca las encuestas del sistema ubicadas en el rango indicado y con el nombre seleccionado.
	 * @param context
	 */
	@Transactional
	public void buscarEncuestaLlenado(UseCaseContext context){
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			context.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
		}
		
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		Usuario usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
		TreeSet set = CollectionFactory.createTreeSet(new NombreComparator());
		set.addAll(usuario.getEncuestasSinLlenar());
		Iterator iEncuestas = set.iterator();
		List list = CollectionFactory.createList();
		while(iEncuestas.hasNext()){
			PlantillaEncuesta encuesta = (PlantillaEncuesta)iEncuestas.next();
			PlantillaEncuesta.definirDescripcionEstado(encuesta);
			if("En Encuesta".equalsIgnoreCase(encuesta.getDescripcionEstado()))
				list.add(encuesta);
		}
		encuestas = list;
		if(encuestas== null || encuestas.isEmpty())
			context.addMessage("No existen encuestas con los parámetros seleccionados");
		context.refreshUseCase();
		
	}
	
	@Secure ({OperationMode.class})
	@Description ("Acceder a Cargar encuestas")
	public void cargarEncuesta(UseCaseContext context){
		Map parametros = CollectionFactory.createMap(String.class,String.class);
		parametros.put(OPERACION, ALTA);
		//context.goToChildUseCase(ABMEncuestaUseCase.class,parametros);
		context.goToChildUseCase(ABMEncuestaUseCase.class, new OperationMode(), parametros, "volverDeABMEncuestas");
	}
	
	
	
	@Secure ({OperationMode.class})
	@Description ("Acceder a eliminar encuestas")
	@Transactional
	public void eliminarEncuesta(UseCaseContext context){
		Map parametros = CollectionFactory.createMap();
		PlantillaEncuesta encuesta = PlantillaEncuesta.findMeById(new Long(super.getIndex()[0]));
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, encuesta );
		parametros.put(OPERACION, ELIMINAR);
		context.goToChildUseCase(ABMEncuestaUseCase.class, new RemoveMode(), parametros, "volverDeABMEncuestas");
	}
	
	@Secure ({OperationMode.class})
	@Description ("Acceder a modificar encuestas")
	@Transactional
	public void modificarEncuesta(UseCaseContext context){
		
		Map parametros = CollectionFactory.createMap();
		PlantillaEncuesta encuesta = PlantillaEncuesta.findMeById(new Long(super.getIndex()[0]) );
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, encuesta );
		parametros.put(OPERACION, MODIFICAR);
		context.goToChildUseCase(ABMEncuestaUseCase.class, new OperationMode(), parametros, "volverDeABMEncuestas");
	}
	
	@Secure ({OperationMode.class})
	@Description ("Acceder a visualizar encuestas")
	@Transactional
	public void visualizarResultadoEncuesta(UseCaseContext context){
		try{
			this.generateReport(context);
		}catch(Exception e){
			context.addMessage("La encuesta fue llenada por ningún usuario");
		}
		
	}
	
	

	
	@Secure ({OperationMode.class})
	@Description ("Acceder a consultar encuestas")
	@Transactional
	public void consultarEncuesta(UseCaseContext context){
		Map parametros = CollectionFactory.createMap();
		PlantillaEncuesta encuesta = PlantillaEncuesta.findMeById(new Long(super.getIndex()[0]));
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, encuesta );
		context.goToChildUseCase(ABMEncuestaUseCase.class, new ViewMode(), parametros, "volverDeABMEncuestas");
	}
	
	@Secure ({OperationMode.class,EditMode.class})
	@Description ("Acceder a Llenar encuestas")
	@Transactional
	public void llenarEncuesta(UseCaseContext context){
		Map parametros = CollectionFactory.createMap();
		PlantillaEncuesta encuesta = PlantillaEncuesta.findMeById(new Long(super.getIndex()[0]));
		parametros.put(OPERACION, LLENAR);
		PlantillaEncuestada encuestada = new PlantillaEncuestada();
		encuestada.setDestinatario(encuesta.getDestinatario());
		encuestada.setNombre(encuesta.getNombre());
		encuestada.setEstado(encuesta.getEstado());
		encuestada.setFechaAlta(encuesta.getFechaAlta());
		encuestada.setFechaFin(encuesta.getFechaFin());
		encuestada.setObligatoriedad(encuestada.getObligatoriedad());
		Collection puntosAEncuestar = encuesta.getPuntosAEncuestar();
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
		parametros.put("PUNTOS_MAP", map);
		parametros.put("PLANTILLA_ENCUESTADA", encuestada);
		parametros.put(ABMBaseUseCaseModel.ENTITY_KEY, encuesta );
		context.goToChildUseCase(ABMEncuestaUseCase.class, new EditMode(), parametros, "volverDeABMEncuestas");
	
	}
	
	
	
	public void cancelar(UseCaseContext context){
		context.acceptUseCase();
	}
	/**
	 * Vuelve del ABMEncuestaUseCaseModel buscando nuevamente las encuestas de acuerdo al último filtro.
	 * @param context
	 */
	@Transactional
	public void volverDeABMEncuestas(UseCaseContext context){
		Usuario usuario;
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			context.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
		}
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
		Collection roles = usuario.getRoles();
		Iterator iRoles = roles.iterator();
		boolean admin = false;
		while(iRoles.hasNext()){
			Rol rol = (Rol)iRoles.next();
			if(RolDomainCodes.ROL_COORDINADOR.equalsIgnoreCase(rol.getDomainCode()) || RolDomainCodes.ROL_ADMIN.equalsIgnoreCase(rol.getDomainCode()))
				admin = true;
			
		}
		if(admin)
			this.buscarEncuesta(context);
		else
			this.buscarEncuestaLlenado(context);
	}
	
	/**GETTERS - SETTERS*/
	
	public Date getFechaFinBusqueda() {
		return fechaFinBusqueda;
	}
	public void setFechaFinBusqueda(Date fechaFinBusqueda) {
		this.fechaFinBusqueda = fechaFinBusqueda;
	}
	public Date getFechaInicioBusqueda() {
		return fechaInicioBusqueda;
	}
	public void setFechaInicioBusqueda(Date fechaInicioBusqueda) {
		this.fechaInicioBusqueda = fechaInicioBusqueda;
	}
	public String getNombrePlantillaBusqueda() {
		return nombrePlantillaBusqueda;
	}
	public void setNombrePlantillaBusqueda(String nombrePlantillaBusqueda) {
		this.nombrePlantillaBusqueda = nombrePlantillaBusqueda;
	}


	public Collection getEncuestas() {
		return encuestas;
	}


	public void setEncuestas(Collection encuestas) {
		this.encuestas = encuestas;
	}
	
	


	public String getLongDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getShortDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	public Class useCaseModelClass() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected List<ModelObject> doFind(UseCaseContext useCaseContext) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected Class<? extends ModelObject> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	public JPEGEncodeParam getImage() {
		return image;
	}
	public void setImage(JPEGEncodeParam image) {
		this.image = image;
	}
	public Boolean getSaved() {
		return saved;
	}
	public void setSaved(Boolean saved) {
		this.saved = saved;
	}
	public String getTipoBuscar() {
		return tipoBuscar;
	}
	public void setTipoBuscar(String tipoBuscar) {
		this.tipoBuscar = tipoBuscar;
	}

	public JRDataSource createReportDatasource() {
		DefaultPieDataset data = new DefaultPieDataset();

		PlantillaEncuesta encuesta = PlantillaEncuesta.findMeById(new Long(super.getIndex()[0]));
		Collection puntosAEncuestar = encuesta.getPuntosAEncuestar();
		

		
			
		Iterator iPuntosAEncuestar = puntosAEncuestar.iterator();
		BigDecimal cantidad = new BigDecimal(0);
		while(iPuntosAEncuestar.hasNext())
		{
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
			punto.getControlador().generarResultado(punto.getPuntosEncuestados());
			cantidad = new BigDecimal(punto.getPuntosEncuestados().size());
			punto.setCantidadEncuestados(cantidad.toString());
			data.setValue("MB", new BigDecimal(punto.getControlador().getPorcentajeMB()).doubleValue());
			data.setValue("B", new BigDecimal(punto.getControlador().getPorcentajeB()).doubleValue());
			data.setValue("R", new BigDecimal(punto.getControlador().getPorcentajeR()).doubleValue());
			data.setValue("I", new BigDecimal(punto.getControlador().getPorcentajeI()).doubleValue());
			JFreeChart chart = ChartFactory.createPieChart3D("", data, true, true, true);
			PiePlot plot = (PiePlot) chart.getPlot();
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}%", NumberFormat.getNumberInstance(),
			NumberFormat.getPercentInstance()));
			plot.setLabelGap(0.1);
			Font font = chart.getLegend().getItemFont();
			font = new Font(font.getName(),font.getStyle(), 26);
			Font font2 = new Font(font.getName(),font.getStyle(), 30);
			chart.getLegend().setItemFont(font); 
			plot.setLabelFont(font);
			TextTitle text = new TextTitle("Resultado reflejado en porcentaje");
			text.setFont(font2);
			chart.setTitle(text);
			
			plot.setLabelBackgroundPaint(Color.LIGHT_GRAY);
			// create and return the image with the size specified in the XML design
			punto.setImage(chart.createBufferedImage(800, 600));
			
		}
		Collection puntosOrdenados = new TreeSet(new NumeroPuntoComparator()); 
		puntosOrdenados.addAll(encuesta.getPuntosAEncuestar());
		puntosAEncuestar = puntosOrdenados;
		return new JRBeanCollectionDataSource(puntosAEncuestar);
	}
	
	
	
	public String getReportName() {
		return "resultadoEncuestas";
	}

	public boolean isResultVisible() {
		return isResultVisible;
	}
	public void setResultVisible(boolean isResultVisible) {
		this.isResultVisible = isResultVisible;
	}
	
	private static class NumeroPuntoComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			PuntoAEncuestar punto = (PuntoAEncuestar)o1;
			PuntoAEncuestar punto2 = (PuntoAEncuestar)o2;
			return punto.getNumero().compareTo(punto2.getNumero());
		}
		
	}
	
}

