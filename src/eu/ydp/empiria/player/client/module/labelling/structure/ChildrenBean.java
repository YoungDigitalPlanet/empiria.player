package eu.ydp.empiria.player.client.module.labelling.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="children")
public class ChildrenBean {

	@XmlElement(name="child")
	private List<ChildBean> children = Lists.newArrayList();
	
	public List<ChildBean> getChildren() {
		return children;
	}

	public void setChildren(List<ChildBean> children) {
		this.children = children;
	}

}
