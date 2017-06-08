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

package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;

public interface GapModulePresenter {

    public static final String WRONG = "wrong";

    public static final String CORRECT = "correct";

    public static final String NONE = "none";

    void setWidth(double value, Unit unit);

    void setHeight(double value, Unit unit);

    int getOffsetWidth();

    int getOffsetHeight();

    void setMaxLength(int length);

    void setFontSize(double value, Unit unit);

    int getFontSize();

    void setText(String text);

    HasWidgets getContainer();

    void installViewInContainer(HasWidgets container);

    void setViewEnabled(boolean enabled);

    void setMarkMode(String mode);

    void removeMarking();
}
