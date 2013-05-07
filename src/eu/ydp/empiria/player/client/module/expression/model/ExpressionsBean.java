package eu.ydp.empiria.player.client.module.expression.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "expressions")
public class ExpressionsBean {

	@XmlElement(name="expression")
	private List<ExpressionBean> expressions;

	public List<ExpressionBean> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<ExpressionBean> expressions) {
		this.expressions = expressions;
	}
}
