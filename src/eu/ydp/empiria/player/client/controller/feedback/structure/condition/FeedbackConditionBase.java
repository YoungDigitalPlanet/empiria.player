package eu.ydp.empiria.player.client.controller.feedback.structure.condition;

import javax.xml.bind.annotation.XmlElement;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class FeedbackConditionBase {
	
	@XmlElement(name="propertyCondition")
	private List<PropertyConditionBean> propertyConditions = new ArrayList<PropertyConditionBean>();
	
	@XmlElement(name="countCondition")
	private List<CountConditionBean> countConditions = new ArrayList<CountConditionBean>();
	
	@XmlElement(name="and")
	private List<AndConditionBean> andConditions = new ArrayList<AndConditionBean>();
	
	@XmlElement(name="or")
	private List<OrConditionBean> orConditions = new ArrayList<OrConditionBean>();
	
	@XmlElement(name="not")
	private List<NotConditionBean> notConditions = new ArrayList<NotConditionBean>();
	
	private List<PropertyConditionBean> getPropertyConditions() {
		return propertyConditions;
	}

	private void setPropertyConditions(List<PropertyConditionBean> propertyCondition) {
		this.propertyConditions = propertyCondition;
	}

	private List<CountConditionBean> getCountConditions() {
		return countConditions;
	}

	private void setCountConditions(List<CountConditionBean> countCondition) {
		this.countConditions = countCondition;
	}

	private List<AndConditionBean> getAndConditions() {
		return andConditions;
	}

	private void setAndConditions(List<AndConditionBean> andConditions) {
		this.andConditions = andConditions;
	}
	
	private List<OrConditionBean> getOrConditions() {
		return orConditions;
	}

	private void setOrConditions(List<OrConditionBean> orConditions) {
		this.orConditions = orConditions;
	}
	
	private List<NotConditionBean> getNotConditions() {
		return notConditions;
	}

	private void setNotConditions(List<NotConditionBean> notConditions) {
		this.notConditions = notConditions;
	}
	
	public List<FeedbackCondition> getAllConditions() {
		List<FeedbackCondition> allConditions = new ArrayList<FeedbackCondition>();
		allConditions.addAll(propertyConditions);
		allConditions.addAll(countConditions);
		allConditions.addAll(andConditions);
		allConditions.addAll(orConditions);
		allConditions.addAll(notConditions);
		
		return allConditions;
	}
}
