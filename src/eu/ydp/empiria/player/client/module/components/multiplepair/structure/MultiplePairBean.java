package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import java.util.List;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

public interface MultiplePairBean<B extends PairChoiceBean> extends ModuleBean, HasShuffle {

	public String getId();

	public String getType();

	public List<B> getSourceChoicesSet();

	public List<B> getTargetChoicesSet();

	public B getChoiceByIdentifier(String sourceItem);

	@Override
	public boolean isShuffle();

	public String getResponseIdentifier();

	public int getMaxAssociations();

}
