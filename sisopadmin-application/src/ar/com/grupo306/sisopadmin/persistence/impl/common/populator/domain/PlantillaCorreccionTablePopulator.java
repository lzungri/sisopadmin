/**
 * 
 */
package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Date;
import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.PlantillaCorreccion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * @author Sole
 *
 */
public class PlantillaCorreccionTablePopulator extends TablePopulator {
	
	Fase fase;
	Consigna consigna;
	
	public void populateFasePrueba(){
		Tp tp = new Tp();
		tp.setNombre("TP Prueba Plantilla");
		tp.setArchivoEspecificacion("TP20072C-EMULASO.pdf");
		tp.save();
		
		fase = new Fase();
		fase.setNombre("Fase 1 - TP Prueba Plantilla");
		fase.setNumero(1);
		fase.setEntregaObligatoria(true);
		fase.setFechaInicio(new Date());
		fase.setFechaFin(DateUtils.addDays(fase.getFechaInicio(), 15));
		fase.setTp(tp);
		fase.saveOrUpdate();
		
		consigna = new Consigna();
		consigna.setNumero(1);
		consigna.setDescripcion("Realizar Fase 1 de TP Prueba Plantilla");
		consigna.setFase(fase);
		consigna.saveOrUpdate();		
		
		Set<Consigna> consignas = CollectionFactory.createSet(Consigna.class);
		consignas.add(consigna);
		
		fase.setConsignas(consignas);
		fase.saveOrUpdate();
		
		Set<Fase> fases = CollectionFactory.createSet(Fase.class);
		fases.add(fase);
		
		tp.setFases(fases);
		tp.save();		
	}
	
	
	public void populatePlantillaCorrección() {
		
		PlantillaCorreccion plantilla = new PlantillaCorreccion();		
		plantilla.setObligatoria(true);		
		plantilla.setFase(fase);
		plantilla.setNombre("Plantilla de Corrección de Prueba");
		plantilla.setPeso(50);
		plantilla.save();
		
		ItemPlantilla item1 = new ItemPlantilla();
		item1.setConsigna(consigna);
		item1.setNombre("ITEM 1");
		item1.setObligatorio(true);
		item1.setProcedimiento("Realizar la Consigna número 1 de la Fase 1 del TP Prueba Plantilla");
		item1.setResultadoEsperado("Consigna realizada completa y satisfactoriamente");
		item1.setObservacionBajaCalificacion("El alumno debe esforzarse más");
		item1.setPeso(75);
		item1.save();
		
		ItemPlantilla item2 = new ItemPlantilla();		
		item2.setObligatorio(false);
		item2.setNombre("ITEM2");
		item2.setProcedimiento("Revisar la disposición del alumno a realizar la Fase 1 del TP Prueba Plantilla");
		item2.setResultadoEsperado("Buena predisposición");
		item2.setObservacionBajaCalificacion("El alumno no está comprometido con la materia");
		item2.setPeso(25);
		item2.save();
		
		Set<ItemPlantilla> items = CollectionFactory.createSet(ItemPlantilla.class);
		items.add(item1);
		items.add(item2);
		
		plantilla.setItems(items);
		plantilla.update();		
	}	 
}
