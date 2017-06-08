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

package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

public interface ExplanationView extends IsWidget {

    void processEntry(Entry entry);

    void show();

    void hide();

    void setExplanationStopButtonStyle();

    void setExplanationPlayButtonStyle();

    void setEntryPlayButtonStyle();

    void setEntryStopButtonStyle();

    void addEntryExamplePanelHandler(MouseUpHandler mouseUpHandler);

    void addPlayButtonHandler(ClickHandler clickHandler);

    void addEntryPlayButtonHandler(ClickHandler handler);

}
