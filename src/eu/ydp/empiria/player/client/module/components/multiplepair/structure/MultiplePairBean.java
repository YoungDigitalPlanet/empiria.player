package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import java.util.List;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

public interface MultiplePairBean extends ModuleBean {

	public String getId();	

	public String getType();

	public List<? extends PairChoiceBean> getFirstChoicesSet();
	
	public List<? extends PairChoiceBean> getSecondChoicesSet();

	public boolean isShuffle();

	public String getResponseIdentifier();

	public int getMaxAssociations();

	public int getMatchMax();
	
}
