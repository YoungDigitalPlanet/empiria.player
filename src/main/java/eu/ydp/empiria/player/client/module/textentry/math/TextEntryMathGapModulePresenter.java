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

package eu.ydp.empiria.player.client.module.textentry.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.gap.DropZoneGuardian;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.empiria.player.client.module.textentry.TextEntryGapModulePresenterBase;

import javax.annotation.PostConstruct;

public class TextEntryMathGapModulePresenter extends TextEntryGapModulePresenterBase implements BlurHandler {

    @UiTemplate("TextEntryMathGap.ui.xml")
    interface TextEntryGapModuleUiBinder extends UiBinder<Widget, TextEntryMathGapModulePresenter> {
    }

    private final TextEntryGapModuleUiBinder uiBinder = GWT.create(TextEntryGapModuleUiBinder.class);

    @UiField
    protected FlowPanel mainPanel;
    @UiField
    protected FlowPanel markPanel;
    @UiField(provided = true)
    protected Widget textBoxWidget;
    private BlurHandler changeHandler;

    @PostConstruct
    public void postConstruct() {
        droppable = dragDropHelper.enableDropForWidget(new TextBox());
        textBoxWidget = droppable.getDroppableWidget();
        textBox = droppable.getOriginalWidget();
        uiBinder.createAndBindUi(this);

        dropZoneGuardian = new DropZoneGuardian(droppable, mainPanel, styleNames);

        textBox.addBlurHandler(this);
    }

    @Override
    public void installViewInContainer(HasWidgets container) {
        container.add(mainPanel);
    }

    @Override
    public HasWidgets getContainer() {
        return mainPanel;
    }

    @Override
    public void setMarkMode(String mode) {
        markPanel.addStyleDependentName(mode);
    }

    @Override
    public void removeMarking() {
        markPanel.removeStyleDependentName(GapModulePresenter.NONE);
        markPanel.removeStyleDependentName(GapModulePresenter.CORRECT);
        markPanel.removeStyleDependentName(GapModulePresenter.WRONG);
    }

    @Override
    public void addPresenterHandler(PresenterHandler handler) {
        changeHandler = handler;
    }

    @Override
    public void onBlur(BlurEvent event) {
        if (changeHandler != null) {
            changeHandler.onBlur(event);
        }
    }
}
