package eu.ydp.empiria.player.client.module.abstractModule.structure;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public abstract class AbstractModuleStructure<InteractionBean extends IInteractionBean> {
	
	JAXBParserFactory<InteractionBean> parser = createParser();
	
	protected InteractionBean interaction;
	
	protected Map<String, Element> feedbacks;
	
	public void createFromXml(String xml) {
		interaction = parse(xml);
		prepareStructure();
		prepareFeedbackNodes(xml);
	}	
	
	private InteractionBean parse(String xml){
		return getParser().parse(xml);
	}	
		
	protected JAXBParser<InteractionBean> getParser() {
		return parser.create();
	}
	
	protected abstract JAXBParserFactory<InteractionBean> createParser();
		
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
	protected void prepareFeedbackNodes(String xml) {
		feedbacks = new HashMap<String, Element>();
	}

	protected Element getInlineFeedbackNode(Element parentNode){
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
