package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import java.util.List;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;

public interface MultiplePairBean<B extends PairChoiceBean> extends ModuleBean {

	public String getId();	

	public String getType();

	public List<B> getSourceChoicesSet();
	
	public List<B> getTargetChoicesSet();

	public B getChoiceByIdentifier(String sourceItem);
	
	public boolean isShuffle();

	public String getResponseIdentifier();

	public int getMaxAssociations();

	public int getMatchMax();
	
}
