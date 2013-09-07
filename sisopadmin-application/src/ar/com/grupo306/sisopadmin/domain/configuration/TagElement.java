package ar.com.grupo306.sisopadmin.domain.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Predicate;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.filter.Filter;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.validation.TagValidatorsIdConstants;
import ar.com.grupo306.sisopadmin.domain.validation.ValidadorTagElement;

/**
 * 
 * @author Rafa
 *
 */

public class TagElement {
	
	Element element;
	private static Map mapFactory;
	private static String VALIDADOR = "validador";
	
	
	/**
	 * Singleton del map que contiene los constructores de los objetos-tags
	 * @return
	 */
	
	private static Map getMapFactory(){
		if(mapFactory == null) mapFactory = createMapFactory();
		return mapFactory;
	}
	/**
	 * Se definen todos los tags que tienen comportamiento
	 * @return
	 */
	private static Map createMapFactory(){
		HashMap map = (HashMap) CollectionFactory.createMap();
		map.put(VALIDADOR , new ValidadorTagElement());
		return map;
	}
	
	public static TagElement getInstance(Element element){
		HashMap tagMapFactory = (HashMap)getMapFactory();
		TagElement tagElement = (TagElement) tagMapFactory.get(element.getName());
		if(tagElement == null)
			tagElement = new TagElement();
		tagElement.setElement(element);
		return tagElement;
	}
	
	
	
	private void setElement(Element element){
		this.element = element;
		
	}
	
	public Element getElement(){
		return element;
	}
	
	/**
	 * Garantiza que el tag debe ser tratado o no corresponde al metodo correspondiente 
	 * @param method
	 * @return
	 */
	
	public boolean checkMethod(String method){
		
		Predicate predicado = new ValidadorPredicate(method);
		return predicado.evaluate(this);
	}
	
	public void populateValidadores(String method , Map map){
		//No hace nada =P
	}
	
	
	/** DELEGATED METHODS - element **/
	
	
	public Element addContent(Comment arg0) {
		return element.addContent(arg0);
	}

	public Element addContent(Element arg0) {
		return element.addContent(arg0);
	}

	public Element addContent(EntityRef arg0) {
		return element.addContent(arg0);
	}

	public Element addContent(ProcessingInstruction arg0) {
		return element.addContent(arg0);
	}

	public Element addContent(String arg0) {
		return element.addContent(arg0);
	}

	public Element addContent(Text arg0) {
		return element.addContent(arg0);
	}

	public void addNamespaceDeclaration(Namespace arg0) {
		element.addNamespaceDeclaration(arg0);
	}

	public Object clone() {
		return element.clone();
	}

	public Element detach() {
		return element.detach();
	}

	public boolean equals(Object arg0) {
		return element.equals(arg0);
	}

	public List getAdditionalNamespaces() {
		return element.getAdditionalNamespaces();
	}

	public Attribute getAttribute(String arg0, Namespace arg1) {
		return element.getAttribute(arg0, arg1);
	}

	public Attribute getAttribute(String arg0) {
		return element.getAttribute(arg0);
	}

	public List getAttributes() {
		return element.getAttributes();
	}

	public String getAttributeValue(String arg0, Namespace arg1, String arg2) {
		return element.getAttributeValue(arg0, arg1, arg2);
	}

	public String getAttributeValue(String arg0, Namespace arg1) {
		return element.getAttributeValue(arg0, arg1);
	}

	public String getAttributeValue(String arg0, String arg1) {
		return element.getAttributeValue(arg0, arg1);
	}

	public String getAttributeValue(String arg0) {
		return element.getAttributeValue(arg0);
	}

	public Element getChild(String arg0, Namespace arg1) {
		return element.getChild(arg0, arg1);
	}

	public Element getChild(String arg0) {
		return element.getChild(arg0);
	}

	public List getChildren() {
		return element.getChildren();
	}

	public List getChildren(String arg0, Namespace arg1) {
		return element.getChildren(arg0, arg1);
	}

	public List getChildren(String arg0) {
		return element.getChildren(arg0);
	}

	public String getChildText(String arg0, Namespace arg1) {
		return element.getChildText(arg0, arg1);
	}

	public String getChildText(String arg0) {
		return element.getChildText(arg0);
	}

	public String getChildTextNormalize(String arg0, Namespace arg1) {
		return element.getChildTextNormalize(arg0, arg1);
	}

	public String getChildTextNormalize(String arg0) {
		return element.getChildTextNormalize(arg0);
	}

	public String getChildTextTrim(String arg0, Namespace arg1) {
		return element.getChildTextTrim(arg0, arg1);
	}

	public String getChildTextTrim(String arg0) {
		return element.getChildTextTrim(arg0);
	}

	public List getContent() {
		return element.getContent();
	}

	public List getContent(Filter arg0) {
		return element.getContent(arg0);
	}

	public Document getDocument() {
		return element.getDocument();
	}

	public String getName() {
		return element.getName();
	}

	public Namespace getNamespace() {
		return element.getNamespace();
	}

	public Namespace getNamespace(String arg0) {
		return element.getNamespace(arg0);
	}

	public String getNamespacePrefix() {
		return element.getNamespacePrefix();
	}

	public String getNamespaceURI() {
		return element.getNamespaceURI();
	}

	public Element getParent() {
		return element.getParent();
	}

	public String getQualifiedName() {
		return element.getQualifiedName();
	}

	public String getText() {
		return element.getText();
	}

	public String getTextNormalize() {
		return element.getTextNormalize();
	}

	public String getTextTrim() {
		return element.getTextTrim();
	}

	public boolean hasChildren() {
		return element.hasChildren();
	}

	public int hashCode() {
		return element.hashCode();
	}

	public boolean isAncestor(Element arg0) {
		return element.isAncestor(arg0);
	}

	public boolean isRootElement() {
		return element.isRootElement();
	}

	public boolean removeAttribute(Attribute arg0) {
		return element.removeAttribute(arg0);
	}

	public boolean removeAttribute(String arg0, Namespace arg1) {
		return element.removeAttribute(arg0, arg1);
	}

	public boolean removeAttribute(String arg0) {
		return element.removeAttribute(arg0);
	}

	public boolean removeChild(String arg0, Namespace arg1) {
		return element.removeChild(arg0, arg1);
	}

	public boolean removeChild(String arg0) {
		return element.removeChild(arg0);
	}

	public boolean removeChildren() {
		return element.removeChildren();
	}

	public boolean removeChildren(String arg0, Namespace arg1) {
		return element.removeChildren(arg0, arg1);
	}

	public boolean removeChildren(String arg0) {
		return element.removeChildren(arg0);
	}

	public boolean removeContent(Comment arg0) {
		return element.removeContent(arg0);
	}

	public boolean removeContent(Element arg0) {
		return element.removeContent(arg0);
	}

	public boolean removeContent(EntityRef arg0) {
		return element.removeContent(arg0);
	}

	public boolean removeContent(ProcessingInstruction arg0) {
		return element.removeContent(arg0);
	}

	public boolean removeContent(Text arg0) {
		return element.removeContent(arg0);
	}

	public void removeNamespaceDeclaration(Namespace arg0) {
		element.removeNamespaceDeclaration(arg0);
	}

	public Element setAttribute(Attribute arg0) {
		return element.setAttribute(arg0);
	}

	public Element setAttribute(String arg0, String arg1, Namespace arg2) {
		return element.setAttribute(arg0, arg1, arg2);
	}

	public Element setAttribute(String arg0, String arg1) {
		return element.setAttribute(arg0, arg1);
	}

	public Element setAttributes(List arg0) {
		return element.setAttributes(arg0);
	}

	public Element setChildren(List arg0) {
		return element.setChildren(arg0);
	}

	public Element setContent(List arg0) {
		return element.setContent(arg0);
	}

	public Element setName(String arg0) {
		return element.setName(arg0);
	}

	public Element setNamespace(Namespace arg0) {
		return element.setNamespace(arg0);
	}

	public Element setText(String arg0) {
		return element.setText(arg0);
	}

	public String toString() {
		return element.toString();
	}
}

/***************************PREDICADOS************************************/

class ValidadorPredicate implements Predicate{
	private String method = "";
	
	public ValidadorPredicate(String method){
		this.method=method;
	}

	public boolean evaluate(Object tag) {
		TagElement tagElement = (TagElement)tag;
		return (tagElement != null && 
				TagValidatorsIdConstants.VALIDADORES.equalsIgnoreCase(tagElement.getName().toString()) ||
				TagValidatorsIdConstants.VALIDADOR.equalsIgnoreCase(tagElement.getName().toString()) ||
				TagValidatorsIdConstants.VALIDADORES_ASOCIADOS.equalsIgnoreCase(tagElement.getName())||
				(TagValidatorsIdConstants.METODO.equalsIgnoreCase(tagElement.getName().toString()) && 
				tagElement.getAttributeValue(TagValidatorsIdConstants.NOMBRE_OPERACION).equalsIgnoreCase(method)));
	    		 
	    		
	}
	
}
