package ar.com.grupo306.usecasefwk.usecases.models.mode;


/**
 * Implementación de un Mode.
 * 
 * @author Sole
 */
public abstract class UseCaseModelModeImpl implements UseCaseModelMode {

	public boolean equals(Object object) {
		if (object == this)
		    return true;
		if (!(object instanceof UseCaseModelMode))
		    return false;
		
		if(object != null) {
			return this.getModeIdentifier().equals(((UseCaseModelMode) object).getModeIdentifier());
		}
		return false;
	}
	
	public int hashCode() {
		return this.getModeIdentifier().hashCode();
	}

}