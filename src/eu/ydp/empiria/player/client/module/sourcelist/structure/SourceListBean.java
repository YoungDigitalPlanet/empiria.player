package eu.ydp.empiria.player.client.module.sourcelist.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

@XmlRootElement(name = "dragDropInteraction")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceListBean implements ModuleBean, HasShuffle {
	@XmlAttribute
	private String id; // NOPMD

	@XmlAttribute(name = "class")
	private String type;

	@XmlAttribute
	private boolean moveElements;

	@XmlAttribute
	private boolean shuffle;

	@XmlElement(name = "simpleSourceListItem")
	private List<SimpleSourceListItemBean> simpleSourceListItemBeans;

	public String getId() {
		return id;
	}

	public void setId(String id) {//NOPMD
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isMoveElements() {
		return moveElements;
	}

	public void setMoveElements(boolean moveElements) {
		this.moveElements = moveElements;
	}

	@Override
	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}

	public List<SimpleSourceListItemBean> getSimpleSourceListItemBeans() {
		return simpleSourceListItemBeans;
	}

	public void setSimpleSourceListItemBeans(List<SimpleSourceListItemBean> simpleSourceListItemBeans) {
		this.simpleSourceListItemBeans = simpleSourceListItemBeans;
	}

}
