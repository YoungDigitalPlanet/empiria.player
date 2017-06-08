/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
