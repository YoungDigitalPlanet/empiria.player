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

package eu.ydp.empiria.player.client.module.inlinechoice;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.core.flow.Activity;
import eu.ydp.empiria.player.client.module.core.flow.Stateful;
import eu.ydp.empiria.player.client.module.core.base.InteractionModuleBase;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.*;

public class InlineChoiceModule extends InteractionModuleBase {

    protected InlineChoiceController controller;

    protected boolean moduleInitialized = false;

    @Inject
    protected Provider<InlineChoicePopupController> inlineChoicePopupControllerProvider;

    @Inject
    protected Provider<InlineChoiceDefaultController> inlineChoiceDefaultControllerProvider;

    @Inject
    protected eu.ydp.gwtutil.client.xml.XMLParser xmlParser;

    @Inject
    private StyleSocket styleSocket;

    public void initModule() {
        setStyles();
        controller.initModule(getModuleSocket(), getEventsBus());
        controller.setParentInlineModule(this);
    }

    protected void setStyles() {
        Map<String, String> styles = getStyles();
        if (styles != null && styles.containsKey(EMPIRIA_INLINECHOICE_TYPE) && styles.get(EMPIRIA_INLINECHOICE_TYPE)
                .equalsIgnoreCase("popup")) {
            controller = inlineChoicePopupControllerProvider.get();
        } else {
            controller = inlineChoiceDefaultControllerProvider.get();
        }
        if (styles != null && styles.containsKey(EMPIRIA_INLINECHOICE_EMPTY_OPTION) && styles.get(EMPIRIA_INLINECHOICE_EMPTY_OPTION)
                .equalsIgnoreCase("hide")) {
            controller.setShowEmptyOption(false);
        } else {
            controller.setShowEmptyOption(true);
        }
        if (styles != null && controller instanceof InlineChoicePopupController && styles.containsKey(EMPIRIA_INLINECHOICE_POPUP_POSITION)
                && styles.get(EMPIRIA_INLINECHOICE_POPUP_POSITION)
                .equalsIgnoreCase("below")) {
            ((InlineChoicePopupController) controller).setPopupPosition(ExListBox.PopupPosition.BELOW);
        }
    }

    protected Map<String, String> getStyles() {
        return styleSocket.getStyles(xmlParser.createDocument()
                .createElement("inlinechoiceinteraction"));
    }

    @Override
    public void addElement(Element element) {
        if (!moduleInitialized) {
            moduleInitialized = true;
            initModule();
            setResponseFromElement(element);
        }
        controller.addElement(element);
    }

    @Override
    public void installViews(List<HasWidgets> placeholders) {
        controller.installViews(placeholders);
    }

    @Override
    public void onBodyLoad() {
        controller.onBodyLoad();
    }

    @Override
    public void onBodyUnload() {
        controller.onBodyUnload();
    }

    @Override
    public void onSetUp() {
        controller.onSetUp();
    }

    @Override
    public void onStart() {
        controller.onStart();
    }

    @Override
    public void onClose() {
    }

    public InlineChoiceController getController() {
        return controller;
    }

    // ------------------------ INTERFACES ------------------------

    @Override
    public void lock(boolean lock) {
        controller.lock(lock);
    }

    @Override
    public void markAnswers(boolean mark) {
        controller.markAnswers(mark);
    }

    @Override
    public void reset() {
        super.reset();
        controller.reset();
    }

    @Override
    public void showCorrectAnswers(boolean show) {

        controller.showCorrectAnswers(show);
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return controller.getJsSocket();
    }

    @Override
    public JSONArray getState() {
        // IMPORTANT: STATE MUST BE COMMON FOR ALL CONTROLLERS
        return controller.getState();
    }

    @Override
    public void setState(JSONArray newState) {
        controller.setState(newState);
    }
}
