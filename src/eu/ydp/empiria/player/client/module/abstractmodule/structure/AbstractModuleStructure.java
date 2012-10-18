package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public abstract class AbstractModuleStructure<M extends ModuleBean> {
	
	JAXBParserFactory<M> parser = createParser();
	
	protected M bean;
	
	protected Map<String, Element> feedbacks;
	
	public void createFromXml(String xml) {
		bean = parse(xml);
		prepareStructure();
		prepareFeedbackNodes(xml);
	}	
	
	public M getBean() {
		return bean;
	}
	
	private M parse(String xml){
		return getParser().parse(xml);
	}	
		
	protected JAXBParser<M> getParser() {
		return parser.create();
	}
	
	protected abstract JAXBParserFactory<M> createParser();
		
	/**
	 * Operates on interaction field ie. for randomize elements, etc.
	 */
	protected abstract void prepareStructure();
	
	/**
	 * Prepares feedbacks map.
	 * Needs to be extended in inheriting module
	 * 
	 * @param xml
	 */
	protected final void prepareFeedbackNodes(String xml) {
		feedbacks = new HashMap<String, Element>();
		Document xmlDocument = XMLParser.parse(xml);
		NodeList nodes = getParentNodesForFeedbacks(xmlDocument);
		
		if(nodes != null){
			for(int i = 0; i < nodes.getLength(); i++){
				Element choiceNode = (Element) nodes.item(i);
				Element feedbackNode = getInlineFeedbackNode(choiceNode);
	
				addFeedbackNode(feedbackNode);
			}
		}
	}
	
	protected abstract NodeList getParentNodesForFeedbacks(Document xmlDocument);

	protected final void addFeedbackNode(Element feedbackNode){
		if(feedbackNode != null){
			String indentifier = feedbackNode.getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
			feedbacks.put(indentifier, feedbackNode);
		}
	}

	protected final Element getInlineFeedbackNode(Element parentNode){
		Element feedbackElement = null;
		NodeList feedbackElements = parentNode.getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK_INLINE);

		if(feedbackElements != null && feedbackElements.getLength() > 0){
			feedbackElement = (Element) feedbackElements.item(0);
		}

		return feedbackElement;
	}	
	
	public Element getFeedbackElement(String identifer){
		return feedbacks.get(identifer);
	}	
}
