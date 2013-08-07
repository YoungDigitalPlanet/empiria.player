package eu.ydp.empiria.player.client.controller.variables.objects.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "responseDeclaration")
public class ResponseBean {

	@XmlAttribute
	private String identifier;

	@XmlAttribute
	private Cardinality cardinality;

	@XmlAttribute
	private Evaluate evaluate;

	@XmlAttribute
	private CheckMode checkMode;

	@XmlAttribute
	private CountMode countMode;
	
	@XmlElement(name = "correctResponse")
	private CorrectResponseBean correctResponse;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Cardinality getCardinality() {
		return cardinality;
	}

	public void setCardinality(Cardinality cardinality) {
		this.cardinality = cardinality;
	}

	public Evaluate getEvaluate() {
		return Objects.firstNonNull(evaluate, Evaluate.DEFAULT);
	}

	public void setEvaluate(Evaluate evaluate) {
		this.evaluate = evaluate;
	}

	public CheckMode getCheckMode() {
		return Objects.firstNonNull(checkMode, CheckMode.DEFAULT);
	}

	public void setCheckMode(CheckMode checkMode) {
		this.checkMode = checkMode;
	}

	public CorrectResponseBean getCorrectResponse() {
		return correctResponse;
	}

	public void setCorrectResponse(CorrectResponseBean correctResponse) {
		this.correctResponse = correctResponse;
	}

	public CountMode getCountMode() {
		return countMode;
	}

	public void setCountMode(CountMode countMode) {
		this.countMode = countMode;
	}

}
