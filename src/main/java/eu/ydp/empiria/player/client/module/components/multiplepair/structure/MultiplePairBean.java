package eu.ydp.empiria.player.client.module.components.multiplepair.structure;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;

import java.util.List;

public interface MultiplePairBean<B extends PairChoiceBean> extends HasShuffle {

    public List<B> getSourceChoicesSet();

    public List<B> getTargetChoicesSet();

    public B getChoiceByIdentifier(String sourceItem);

    @Override
    public boolean isShuffle();

    public int getMaxAssociations();

    public int getRightItemIndex(PairChoiceBean bean);

    public int getLeftItemIndex(PairChoiceBean bean);

    public boolean isLeftItem(PairChoiceBean bean);
}
