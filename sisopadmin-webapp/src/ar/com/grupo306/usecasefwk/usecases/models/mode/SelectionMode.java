package ar.com.grupo306.usecasefwk.usecases.models.mode;

/**
 * Modo de selección.
 *
 * @author Leandro
 */
public class SelectionMode extends UseCaseModelModeImpl {
	private Integer maxSelection = new Integer(1);

	public SelectionMode() {
		super();
	}
	
	/**
	 * Crea un modo que permite seleccionar como máximo <maxSelection> elementos.
	 * @param multipleSelect
	 */
	public SelectionMode(Integer maxSelection) {
		this.maxSelection = maxSelection;
	}
	
	public String getModeIdentifier() {
		return "selection";
	}

	public Integer getMaxSelection() {
		return maxSelection;
	}

	public void setMaxSelection(Integer maxSelection) {
		this.maxSelection = maxSelection;
	}

}