package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public abstract class VariableProcessor {

	public abstract void ensureVariables(Map<String, Response> responses, Map<String, Outcome> outcomes);
	
	public abstract void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, boolean userInteract);
	
	public abstract void processFlowActivityVariables(Map<String, Outcome> outcomes, FlowActivityEvent event);
	
	public static void interpretFeedbackAutoMark(Node node, Map<String, Response> responses){
		NodeList feedbacks =  ((Element)node).getElementsByTagName("feedbackInline");
		
		for (int f = 0 ; f < feedbacks.getLength() ; f ++){
			if (((Element)feedbacks.item(f)).hasAttribute("variableIdentifier")  &&  ((Element)feedbacks.item(f)).hasAttribute("value")  &&  ((Element)feedbacks.item(f)).hasAttribute("mark")){
				if (feedbacks.item(f).getAttributes().getNamedItem("mark").getNodeValue().toUpperCase().compareTo("AUTO") == 0){
					String variableIdentifier = feedbacks.item(f).getAttributes().getNamedItem("variableIdentifier").getNodeValue();
					String value = feedbacks.item(f).getAttributes().getNamedItem("value").getNodeValue();
					if (responses.containsKey(variableIdentifier)){
						boolean correctness = false;
						boolean correctnessFound = false;
						if (value.contains(".")  || 
							value.contains("*")  ||
							value.contains("[")  ||
							value.contains("(")  ||
							value.contains("\\")  ||
							value.contains("^")  ||
							value.contains("$")  ||
							value.contains("]")  ||
							value.contains(")")){
							
							String responseCorrectAnswers = responses.get(variableIdentifier).getCorrectAnswersValuesShort();
							correctness = responseCorrectAnswers.matches(value);
							correctnessFound = true;
							
						} else {
							correctness = responses.get(variableIdentifier).compareValues(value.split(";"));
							correctnessFound = true;
						}
						if (correctnessFound){
							if (correctness)
								feedbacks.item(f).getAttributes().getNamedItem("mark").setNodeValue("CORRECT");
							else
								feedbacks.item(f).getAttributes().getNamedItem("mark").setNodeValue("WRONG");
						} else {
							feedbacks.item(f).getAttributes().getNamedItem("mark").setNodeValue("NONE");
						}
					}
				}
			}
		}
	}
}
