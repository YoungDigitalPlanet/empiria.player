package eu.ydp.empiria.player.client.controller.feedback.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class FeedbackConditionBase {
	
	@XmlElement(name="propertyCondition")
	private List<PropertyConditionBean> propertyConditions = Lists.newArrayList();
	
	@XmlElement(name="countCondition")
	private List<CountConditionBean> countConditions = Lists.newArrayList();
	
	@XmlElement(name="and")
	private List<AndConditionBean> andConditions = Lists.newArrayList();
	
	@XmlElement(name="or")
	private List<OrConditionBean> orConditions = Lists.newArrayList();
	
	@XmlElement(name="not")
	private List<NotConditionBean> notConditions = Lists.newArrayList();
	
	public List<PropertyConditionBean> getPropertyConditions() {
		return propertyConditions;
	}

	public void setPropertyConditions(List<PropertyConditionBean> propertyCondition) {
		this.propertyConditions = propertyCondition;
	}

	public List<CountConditionBean> getCountConditions() {
		return countConditions;
	}

	public void setCountConditions(List<CountConditionBean> countCondition) {
		this.countConditions = countCondition;
	}

	public List<AndConditionBean> getAndConditions() {
		return andConditions;
	}

	public void setAndConditions(List<AndConditionBean> andConditions) {
		this.andConditions = andConditions;
	}
	
	public List<OrConditionBean> getOrConditions() {
		return orConditions;
	}

	public void setOrConditions(List<OrConditionBean> orConditions) {
		this.orConditions = orConditions;
	}
	
	public List<NotConditionBean> getNotConditions() {
		return notConditions;
	}

	public void setNotConditions(List<NotConditionBean> notConditions) {
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
