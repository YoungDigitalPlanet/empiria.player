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

package eu.ydp.empiria.player.client.module.pageswitch;

import com.google.gwt.user.client.ui.ListBox;

public class PageSwitchListBox extends ListBox implements IPageSwitchWidget {

    @Override
    public void setItemsCount(Integer itemsCount) {
        initializeList(itemsCount);
    }

    @Override
    public void setCurrentIndex(Integer value) {
        setSelectedIndex(value);
    }

    @Override
    public Integer getCurrentIndex() {
        return getSelectedIndex();
    }

    @Override
    public void enable() {
        setEnabled(true);
    }

    @Override
    public void disable() {
        setEnabled(false);
    }

    private void initializeList(Integer itemsNum) {
        Integer itemIndex;

        for (Integer i = 0; i < itemsNum; i++) {
            itemIndex = i + 1;
            addItem(itemIndex.toString());
        }
    }
}
