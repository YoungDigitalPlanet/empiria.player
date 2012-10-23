package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

public interface PairChoiceBean {
	public abstract String getIdentifier();

	public abstract int getMatchMax();

	public abstract XMLContent getXmlContent();

	public abstract boolean isFixed();
}
