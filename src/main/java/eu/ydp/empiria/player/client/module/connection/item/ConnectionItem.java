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

package eu.ydp.empiria.player.client.module.connection.item;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;

public interface ConnectionItem extends IsWidget {
    public static enum Column {
        LEFT, RIGHT
    }

    Column getColumn();

    public PairChoiceBean getBean();

    public void reset();

    public void setConnected(boolean connected, MultiplePairModuleConnectType connectType);

    public int getRelativeX();

    public int getRelativeY();

    public int getOffsetLeft();

    public int getOffsetTop();

    public int getWidth();

    public int getHeight();
}
