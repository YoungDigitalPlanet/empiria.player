package eu.ydp.empiria.player.client.module.sourcelist.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

@XmlRootElement(name = "sourceList")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceListBean implements ModuleBean, HasShuffle {
	@XmlAttribute
	private String id; //NOPMD

	@XmlAttribute(name = "class")
	private String type;

	@XmlAttribute
	private boolean moveElements;

	@XmlAttribute
	private boolean shuffle;

	@XmlElement(name="simpleSourceListItem")
	private List<SimpleSourceListItemBean> simpleSourceListItemBeans;

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public boolean isMoveElements() {
		return moveElements;
	}

	@Override
	public boolean isShuffle() {
		return shuffle;
	}

	public List<SimpleSourceListItemBean> getSimpleSourceListItemBeans() {
		return simpleSourceListItemBeans;
	}

	public void setSimpleSourceListItemBeans(List<SimpleSourceListItemBean> simpleSourceListItemBeans) {
		this.simpleSourceListItemBeans = simpleSourceListItemBeans;
	}


}
