package eu.ydp.empiria.player.client.module.external.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.StringUtils;
import javax.xml.bind.annotation.XmlAttribute;

public class ExternalInteractionModuleBean extends ModuleBean {

	@XmlAttribute(name = "src")
	private String src = StringUtils.EMPTY_STRING;

	@XmlAttribute(name = "todo")
	private Integer todo = 0;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getTodo() {
		return todo;
	}

	public void setTodo(Integer todo) {
		this.todo = todo;
	}
}
