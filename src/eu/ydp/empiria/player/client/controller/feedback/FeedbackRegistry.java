package eu.ydp.empiria.player.client.controller.feedback;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public class FeedbackRegistry {
	
	@Inject
	private FeedbackParserFactory feedbackParserFactory;
	
	private final Map<IModule, List<Feedback>> modules2feedbacks = Maps.newHashMap();
	
	public void registerFeedbacks(IModule module, Node moduleNode){
		Element moduleElement = (Element) moduleNode;
		NodeList feedbackNodeList = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK);
		
		if(feedbackNodeList != null && feedbackNodeList.getLength() > 0){
			addModuleFeedbacks(module, feedbackNodeList);
		}
	}
	
	private void addModuleFeedbacks(IModule module, NodeList feedbackNodeList){
		List<Feedback> feedbackList = getModuleFeedbacks(module);
		feedbackList.addAll(createFeedbackList(feedbackNodeList));
		modules2feedbacks.put(module, feedbackList);
	}
	
	public boolean isModuleRegistered(IModule module){
		return modules2feedbacks.get(module) != null;
	}
	
	public List<Feedback> getModuleFeedbacks(IModule module){
		List<Feedback> feedbackList = modules2feedbacks.get(module);
		
		if(feedbackList == null){
			feedbackList = Lists.newArrayList();
		}
		
		return feedbackList;
	}
	
	private List<Feedback> createFeedbackList(NodeList feedbackNodeList){
		List<Feedback> feedbackList = Lists.newArrayList();
		
		for(int i = 0; i < feedbackNodeList.getLength(); i++){
			Node feedbackNode = feedbackNodeList.item(i);
			feedbackList.add(createFeedback(feedbackNode));
		}
		
		return feedbackList;
	}
	
	private Feedback createFeedback(Node feedbackNode){
		return getFeedbackParserFactory().create().parse(feedbackNode.toString());
	}
	
	FeedbackParserFactory getFeedbackParserFactory(){
		return feedbackParserFactory;
	}
	
}
