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

package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.tutor.TutorStyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

import javax.annotation.PostConstruct;
import java.util.List;

public class TutorPopupViewImpl implements TutorPopupView {

    @Inject
    private TutorPopupViewWidget popupViewWidget;
    @Inject
    private TutorStyleNameConstants styleNameConstants;
    @Inject
    private Provider<TutorPopupViewPersonaView> personasViewProvider;
    @Inject
    private RootPanelDelegate rootPanelDelegate;
    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    private final Command hideCommand = new Command() {

        @Override
        public void execute(NativeEvent event) {
            hide();
        }
    };

    @PostConstruct
    public void postConstruct() {
        userInteractionHandlerFactory.applyUserClickHandler(hideCommand, popupViewWidget);
        userInteractionHandlerFactory.applyUserClickHandler(hideCommand, popupViewWidget.getCloseButton());
    }

    @Override
    public void setSelected(int personaIndex) {
        unselectAllPersonas();
        popupViewWidget.getWidget(personaIndex).setStyleName(styleNameConstants.QP_TUTOR_POPUP_SELECTED_PERSONA());
    }

    private void unselectAllPersonas() {
        List<Widget> allWidgets = popupViewWidget.getAllWidgets();
        for (Widget widget : allWidgets) {
            widget.setStyleName(styleNameConstants.QP_TUTOR_POPUP_ITEM());
        }
    }

    @Override
    public void show() {
        rootPanelDelegate.getRootPanel().add(popupViewWidget);
    }

    @Override
    public void hide() {
        rootPanelDelegate.getRootPanel().remove(popupViewWidget);
    }

    @Override
    public void addPersona(PersonaViewDto personaViewDto) {
        final TutorPopupViewPersonaView personaView = personasViewProvider.get();
        personaView.setAvatarUrl(personaViewDto.getAvatarUrl());
        popupViewWidget.addWidget(personaView);
    }

    @Override
    public void addClickHandlerToPersona(Command command, int personaIndex) {
        Widget personaWidget = popupViewWidget.getWidget(personaIndex);
        userInteractionHandlerFactory.applyUserClickHandler(command, personaWidget);
    }

}
