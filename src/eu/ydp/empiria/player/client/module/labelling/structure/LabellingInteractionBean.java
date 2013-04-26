package eu.ydp.empiria.player.client.module.labelling.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labellingInteraction")
public class LabellingInteractionBean {

	@XmlElement
	private ImgBean img;
	@XmlElement
	private ChildrenBean children;

	public ImgBean getImg() {
		return img;
	}

	public void setImg(ImgBean img) {
		this.img = img;
	}

	public ChildrenBean getChildren() {
		return children;
	}

	public void setChildren(ChildrenBean children) {
		this.children = children;
	}

}
