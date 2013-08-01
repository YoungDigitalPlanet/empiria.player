package eu.ydp.empiria.player.client.module.labelling.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.base.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labellingInteraction")
public class LabellingInteractionBean {

	@XmlElement private ImgBean img;
	@XmlElement private ChildrenBean children;
	@XmlAttribute private String id;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return Objects.firstNonNull(id, "");
	}

}
