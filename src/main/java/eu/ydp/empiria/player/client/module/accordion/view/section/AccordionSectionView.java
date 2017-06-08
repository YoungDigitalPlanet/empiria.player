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

package eu.ydp.empiria.player.client.module.accordion.view.section;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface AccordionSectionView extends IsWidget {
    void setTitle(Widget widget);

    HasWidgets getContentContainer();

    void addClickCommand(Command clickCommand);

    void addSectionStyleName(String style);

    void addContentWrapperStyleName(String style);

    void removeSectionStyleName(String style);

    void removeContentWrapperStyleName(String style);

    int getContentHeight();

    int getContentWidth();

    void setSectionDimensions(String width, String height);
}
