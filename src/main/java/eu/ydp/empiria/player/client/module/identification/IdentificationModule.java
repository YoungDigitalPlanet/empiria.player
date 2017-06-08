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

package eu.ydp.empiria.player.client.module.identification;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.InteractionModuleBase;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IdentificationModule extends InteractionModuleBase {

    private int maxSelections;
    private boolean locked = false;
    private boolean showingCorrectAnswers = false;

    @Inject
    private UserInteractionHandlerFactory interactionHandlerFactory;
    @Inject
    private IdentificationModuleFactory identificationModuleFactory;
    @Inject
    private IdentificationChoicesManager choicesManager;

    private final List<Element> multiViewElements = new ArrayList<>();

    @Override
    public void addElement(Element element) {
        multiViewElements.add(element);
    }

    @Override
    public void installViews(List<HasWidgets> placeholders) {
        setResponseFromElement(multiViewElements.get(0));
        maxSelections = XMLUtils.getAttributeAsInt(multiViewElements.get(0), "maxSelections");

        for (int i = 0; i < multiViewElements.size(); i++) {
            Element element = multiViewElements.get(i);
            SelectableChoicePresenter selectableChoice = createSelectableChoiceFromElement(element);

            addClickHandler(selectableChoice);

            HasWidgets currPlaceholder = placeholders.get(i);
            currPlaceholder.add(selectableChoice.getView());
            choicesManager.addChoice(selectableChoice);
        }
    }

    private SelectableChoicePresenter createSelectableChoiceFromElement(Element element) {
        Node simpleChoice = element.getElementsByTagName("simpleChoice").item(0);
        SelectableChoicePresenter selectableChoice = createSelectableChoice((Element) simpleChoice);

        return selectableChoice;
    }

    private SelectableChoicePresenter createSelectableChoice(Element item) {
        String identifier = XMLUtils.getAttributeAsString(item, "identifier");
        InlineBodyGeneratorSocket inlineBodyGeneratorSocket = getModuleSocket().getInlineBodyGeneratorSocket();
        Widget contentWidget = inlineBodyGeneratorSocket.generateInlineBody(item);
        SelectableChoicePresenter selectableChoice = identificationModuleFactory.createSelectableChoice(contentWidget, identifier);

        return selectableChoice;
    }

    private void addClickHandler(final SelectableChoicePresenter selectableChoice) {
        Command clickCommand = createClickCommand(selectableChoice);
        EventHandlerProxy userClickHandler = interactionHandlerFactory.createUserClickHandler(clickCommand);
        userClickHandler.apply(selectableChoice.getView());
    }

    private Command createClickCommand(final SelectableChoicePresenter selectableChoice) {
        return new Command() {

            @Override
            public void execute(NativeEvent event) {
                onChoiceClick(selectableChoice);
            }
        };
    }

    @Override
    public void lock(boolean locked) {
        this.locked = locked;
        if (locked) {
            choicesManager.lockAll();
        } else {
            choicesManager.unlockAll();
        }
    }

    @Override
    public void markAnswers(boolean mark) {
        CorrectAnswers correctAnswers = getResponse().correctAnswers;
        choicesManager.markAnswers(mark, correctAnswers);
    }

    @Override
    public void reset() {
        super.reset();
        markAnswers(false);
        lock(false);
        choicesManager.clearSelections();
        updateResponse(false, true);
    }

    @Override
    public void showCorrectAnswers(boolean show) {
        if (show) {
            CorrectAnswers correctAnswers = getResponse().correctAnswers;
            choicesManager.selectCorrectAnswers(correctAnswers);
        } else {
            List<String> values = getResponse().values;
            choicesManager.restoreView(values);
        }
        showingCorrectAnswers = show;
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return ModuleJsSocketFactory.createSocketObject(this);
    }

    @Override
    public JSONArray getState() {
        return choicesManager.getState();
    }

    @Override
    public void setState(JSONArray newState) {
        choicesManager.setState(newState);
        updateResponse(false);
    }

    private void onChoiceClick(SelectableChoicePresenter selectableChoice) {
        if (!locked) {
            selectableChoice.setSelected(!selectableChoice.isSelected());
            Collection<SelectableChoicePresenter> selectedOptions = choicesManager.getSelectedChoices();
            int currSelectionsCount = selectedOptions.size();
            if (currSelectionsCount > maxSelections) {
                for (SelectableChoicePresenter choice : selectedOptions) {
                    if (selectableChoice != choice) {
                        choice.setSelected(false);
                        break;
                    }
                }
            }
            updateResponse(true);
        }
    }

    private void updateResponse(boolean userInteract) {
        updateResponse(userInteract, false);
    }

    private void updateResponse(boolean userInteract, boolean isReset) {
        if (!showingCorrectAnswers) {
            List<String> currResponseValues = choicesManager.getIdentifiersSelectedChoices();

            if (!getResponse().compare(currResponseValues) || !getResponse().isInitialized()) {
                getResponse().set(currResponseValues);
                fireStateChanged(userInteract, isReset);
            }
        }
    }

    @Override
    public void onBodyLoad() {
    }

    @Override
    public void onBodyUnload() {
    }

    @Override
    public void onSetUp() {
        updateResponse(false);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onClose() {
    }
}
