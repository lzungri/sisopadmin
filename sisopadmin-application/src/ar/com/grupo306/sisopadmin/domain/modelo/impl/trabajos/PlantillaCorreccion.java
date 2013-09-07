package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class PlantillaCorreccion extends ModelObject{
	private Fase fase;
	private Set<ItemPlantilla> items;
	private Integer peso;
	private Boolean obligatoria = Boolean.FALSE;
	private String nombre;
	
	public Integer getSumatoriaPesosItems() {
		Integer sumatoria = 0;
		
		for(ItemPlantilla item : items) {
			sumatoria += item.getPeso();
		}
		return sumatoria;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer pesoPlantilla) {
		this.peso = pesoPlantilla;
	}
	public Set<ItemPlantilla> getItems() {
		if (items == null)
			items = CollectionFactory.createSet(ItemPlantilla.class);
		return items;
	}

	public void setItems(Set<ItemPlantilla> items) {
		this.items = items;
	}
	
	public static List findByFase(Fase fase){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaCorreccion.class);
		criteria.add(Restrictions.sqlRestriction("ID_FASE = " + fase.getId() )); 			
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
	}
	
	public static List findUs(Fase fase, Integer peso, Integer cantItem, String nombre){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(PlantillaCorreccion.class);
		
		if(fase != null)
			criteria.add(Restrictions.sqlRestriction("ID_FASE = " + fase.getId() ));
		
		if(peso != null)
			criteria.add(Expression.like("peso", peso));	
		
		criteria.add(Expression.like(NOMBRE,"%" + nombre + "%"));
		
		List result = SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);
		
		if(cantItem != null){			
			List<PlantillaCorreccion> plantillas = CollectionFactory.createList(PlantillaCorreccion.class);
					
			for(Iterator it = result.iterator(); it.hasNext();){
				PlantillaCorreccion plantilla = (PlantillaCorreccion)it.next();
				if( plantilla.getItems().size() == cantItem){
					plantillas.add(plantilla);
				}
			}		
			return plantillas;
		}
		return result;
	}

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public Boolean getObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(Boolean obligatoria) {
		this.obligatoria = obligatoria;
	}
	
	public Boolean isUsed(){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(EvaluacionFasePorPlantilla.class);;
		criteria.add(Expression.eq("plantilla", this));
		return (SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).size() > 0);
	}

}