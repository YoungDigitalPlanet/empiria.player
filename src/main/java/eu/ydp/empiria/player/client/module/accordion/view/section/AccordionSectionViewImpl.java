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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class AccordionSectionViewImpl extends Composite implements AccordionSectionView {
    private static AccordionSectionUiBinder uiBinder = GWT.create(AccordionSectionUiBinder.class);

    @UiTemplate("AccordionSectionView.ui.xml")
    interface AccordionSectionUiBinder extends UiBinder<Widget, AccordionSectionViewImpl> {
    }

    @UiField
    SimplePanel title;
    @UiField
    FlowPanel content;
    @UiField
    SimplePanel contentWrapper;
    @UiField
    FlowPanel section;

    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    @Inject
    public AccordionSectionViewImpl(UserInteractionHandlerFactory userInteractionHandlerFactory) {
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTitle(Widget widget) {
        title.setWidget(widget);
    }

    @Override
    public HasWidgets getContentContainer() {
        return content;
    }

    @Override
    public void addClickCommand(Command clickCommand) {
        userInteractionHandlerFactory.applyUserClickHandler(clickCommand, title);
    }

    @Override
    public void addSectionStyleName(String style) {
        section.addStyleName(style);
    }

    @Override
    public void addContentWrapperStyleName(String style) {
        contentWrapper.addStyleName(style);
    }

    @Override
    public void removeSectionStyleName(String style) {
        section.removeStyleName(style);
    }

    @Override
    public void removeContentWrapperStyleName(String style) {
        contentWrapper.removeStyleName(style);
    }

    @Override
    public int getContentHeight() {
        return content.getOffsetHeight();
    }

    @Override
    public int getContentWidth() {
        return content.getOffsetWidth();
    }

    @Override
    public void setSectionDimensions(String width, String height) {
        contentWrapper.setWidth(width);
        contentWrapper.setHeight(height);
    }
}
