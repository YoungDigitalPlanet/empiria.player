package eu.ydp.empiria.player.client.controller.feedback;

import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.util.xml.XMLConverter;

public class AssessmentFeedbackCase {
	public AssessmentFeedbackCase(Node node){
		
		contents = new InlineHTML();
		contents.setStyleName("qp-feedback-assessment-contents");
		
		container = new FlowPanel();
		container.setStyleName("qp-feedback-assessment");
		container.add(contents);
			
		percentCondition = Integer.parseInt( node.getAttributes().getNamedItem("percentageScore").getNodeValue() );
		contentsSource = XMLConverter.getDOM((Element)node, new Vector<String>()).getInnerHTML();
		
		contents.setHTML(contentsSource);
	}
	
	private int percentCondition;
	private String contentsSource;
	
	private FlowPanel container;
	private InlineHTML contents;
	
	public FlowPanel getView(){
		return container;
	}
	
	public boolean checkCondition(int percentageResult){
		return (percentageResult >= percentCondition);
	}
}

