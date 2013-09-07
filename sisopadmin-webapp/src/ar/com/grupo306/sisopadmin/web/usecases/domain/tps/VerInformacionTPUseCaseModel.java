/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

import fr.improve.struts.taglib.layout.sort.SortUtil;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * @author Sole
 *
 */
public class VerInformacionTPUseCaseModel extends BaseUseCaseModel {
	private Tp tp;
	private String infoConsignas = "";

	@Override
	@Transactional
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);	
		tp = (Tp) context.getParameters().get(ABMBaseUseCaseModel.ENTITY_KEY);
		
		// Para que las fases aparezcan ordenadas
		SortedSet fasesOrdenadas = CollectionFactory.createTreeSet(new Comparator() {
		
			public int compare(Object o1, Object o2) {
				return((Fase)o1).getNumero().compareTo(((Fase)o2).getNumero());				
			}		
		});
		fasesOrdenadas.addAll(tp.getFases());	
		
		SortedSet consignasOrdenadas = CollectionFactory.createTreeSet(new Comparator() {
			
			public int compare(Object o1, Object o2) {
				return((Consigna)o1).getNumero().compareTo(((Consigna)o2).getNumero());				
			}		
		});
		
		for(Iterator it= fasesOrdenadas.iterator(); it.hasNext();){
			Fase fase = (Fase)it.next();			
			infoConsignas = infoConsignas.concat("<h4>Fase " + fase.getNumero() + " - " + fase.getNombre() + "</h4>");
			consignasOrdenadas.clear();
			consignasOrdenadas.addAll(fase.getConsignas());
			for(Iterator itC = consignasOrdenadas.iterator();itC.hasNext();){
				Consigna consigna = (Consigna)itC.next();
				infoConsignas = infoConsignas.concat("<i><u>Consigna " + consigna.getNumero() + ":</u></i>");
				infoConsignas = infoConsignas.concat(" " + consigna.getDescripcion() + "<br>");
			}
			infoConsignas = infoConsignas.concat("<br>");			
		}
	}

	public Tp getTp() {
		return tp;
	}

	public void setTp(Tp tp) {
		this.tp = tp;
	}

	public String getInfoConsignas() {
		return infoConsignas;
	}

	public void setInfoConsignas(String infoConsignas) {
		this.infoConsignas = infoConsignas;
	}
	
	
	
}

