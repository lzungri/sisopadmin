/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import java.util.List;
import java.util.Set;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.PlantillaCorreccion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
/**
 * @author Sole
 *
 */
public class AdministrarPlantillasUseCaseModel extends AdminBaseUseCaseModel {
	@Override
	protected boolean prePopulateUseCase() {
		return true;
	}
	private Tp tp = new Tp();
	private Set<Fase> fases = CollectionFactory.createSet(Fase.class);
	private String id_fase;	
	private Integer peso;
	private Integer cantidadItems;		
	private String nombre;

	@Override
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		super.returnFromChildDefaultMethod(context);
		Fase fase = null;
		
		if(context.getReturnedModel() != null){
			try{
			SelectFaseUseCaseModel model = (SelectFaseUseCaseModel)context.getReturnedModel();
			this.fases = model.getTp().getFases();
			id_fase = model.getId_fase();
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		
		if(id_fase != null && id_fase.compareTo("") != 0){
			fase = Fase.findMeById(Long.parseLong(id_fase));
		}
		List plantillasList = CollectionFactory.createList();
		plantillasList.addAll(PlantillaCorreccion.findUs(fase, peso, cantidadItems, nombre));
		this.setEntities(plantillasList);		
	
	}

	@Override
	@Secure ({})
	@Description ("Acceder a Administrar Plantillas")
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {		
		super.initUseCase(useCaseContext);		
	}
	
	@Override
	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {		
		return ABMPlantillaUseCase.class;
	}
	@Override
	protected List<ModelObject> doFind(UseCaseContext useCaseContext) {
		Fase fase = null;
		if(id_fase != null){
			fase = Fase.findMeById(Long.parseLong(id_fase));
		}
		List plantillasList = CollectionFactory.createList();
		plantillasList.addAll(PlantillaCorreccion.findUs(fase, peso, cantidadItems, nombre));
		return plantillasList;
	}
	
	@Override
	protected Class<? extends ModelObject> entityClass() {
		return PlantillaCorreccion.class;
	}	
	
	@Override
	@Transactional
	public void edit(UseCaseContext useCaseContext) {
		PlantillaCorreccion plantilla = (PlantillaCorreccion) getSelectedEntity(useCaseContext);
		if(plantilla.isUsed()){
			useCaseContext.addErrorMessage("La Plantilla " + plantilla.getNombre() + " no puede modificarse porque tiene asociadas evaluaciones");
		}
		else{
			super.edit(useCaseContext);
		}
	}

	@Override
	@Transactional
	public void remove(UseCaseContext useCaseContext) {
		PlantillaCorreccion plantilla = (PlantillaCorreccion) getSelectedEntity(useCaseContext);
		if(plantilla.isUsed()){
			useCaseContext.addErrorMessage("La Plantilla " + plantilla.getNombre() + " no puede eliminarse porque tiene asociadas evaluaciones");
		}
		else{
			super.remove(useCaseContext);
		}		
	}

	// SELECCION DE TP
	public void seleccionarTP(UseCaseContext context){
		context.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionTP");
	}
	
	public void returnFromSeleccionTP(UseCaseContext context) {
		AdministrarTPsUseCaseModel model = ((AdministrarTPsUseCaseModel)context.getReturnedModel());
		if(model != null){
			this.setTp((Tp)model.getSelectedEntities().iterator().next());
			fases = tp.getFases();
		}
		else{
			this.setTp(new Tp());
			fases = CollectionFactory.createSet(Fase.class);
			id_fase = null;
		}
		
	}
	
	public void cargarPlantilla(UseCaseContext context){
		context.goToChildUseCase(SelectFaseUseCase.class);
	}
	public Integer getCantidadItems() {
		return cantidadItems;
	}
	public void setCantidadItems(Integer cantidadItems) {
		this.cantidadItems = cantidadItems;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	public Set<Fase> getFases() {
		return fases;
	}
	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}
	public Tp getTp() {
		return tp;
	}
	public void setTp(Tp tp) {
		this.tp = tp;
	}
	public String getId_fase() {
		return id_fase;
	}
	public void setId_fase(String id_fase) {
		this.id_fase = id_fase;
	}	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
