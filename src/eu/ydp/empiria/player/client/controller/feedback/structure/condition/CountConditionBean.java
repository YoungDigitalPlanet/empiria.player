package eu.ydp.empiria.player.client.controller.feedback.structure.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="countCondition")
public class CountConditionBean extends FeedbackConditionBase implements FeedbackCondition {
	
	@XmlAttribute(name="count")
	private String count;
	
	@XmlAttribute(name="operator")
	private String operator;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}
