package eu.ydp.empiria.player.client.module.sourcelist.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.ModuleBean;

@XmlRootElement(name = "sourceList")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceListBean extends ModuleBean implements HasShuffle {

	@XmlAttribute
	private boolean moveElements;

	@XmlAttribute
	private boolean shuffle;

	@XmlElement(name = "simpleSourceListItem")
	private List<SimpleSourceListItemBean> simpleSourceListItemBeans = Lists.newArrayList();

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
		return ImmutableList.copyOf(simpleSourceListItemBeans);
	}

	public void setSimpleSourceListItemBeans(List<SimpleSourceListItemBean> simpleSourceListItemBeans) {
		this.simpleSourceListItemBeans = simpleSourceListItemBeans;
	}

}
