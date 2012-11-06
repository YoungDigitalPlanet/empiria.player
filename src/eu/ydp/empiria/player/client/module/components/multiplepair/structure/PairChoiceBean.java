package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;

public interface PairChoiceBean extends HasFixed {
	public abstract String getIdentifier();

	public abstract int getMatchMax();

	public abstract XMLContent getXmlContent();


}
