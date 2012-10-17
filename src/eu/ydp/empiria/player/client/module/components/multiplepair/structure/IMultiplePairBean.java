package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import java.util.List;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.IInteractionBean;

public interface IMultiplePairBean extends IInteractionBean {

	public List<? extends AMultiplePairChoiceBean> getFirstChoicesSet();
	
	public List<? extends AMultiplePairChoiceBean> getSecondChoicesSet();
	
}
