package eu.ydp.empiria.player.client.controller.feedback.structure.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="countCondition")
public class CountConditionBean extends FeedbackConditionBase implements FeedbackCondition {
	
	@XmlAttribute(name="count")
	private Integer count;
	
	@XmlAttribute(name="operator")
	private String operator;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public FeedbackCondition getCondition() {
		return getAllConditions().get(0);
	}
}
