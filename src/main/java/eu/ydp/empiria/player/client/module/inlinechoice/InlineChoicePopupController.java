package eu.ydp.empiria.player.client.module.inlinechoice;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEvent;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.controller.multiview.touch.SwypeBlocker;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.module.core.base.ParentedModuleBase;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxChangeListener;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.ArrayList;
import java.util.List;

import static eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEventTypes.RESET_COUNTER;

public class InlineChoicePopupController extends ParentedModuleBase implements InlineChoiceController, ExListBoxChangeListener, PlayerEventHandler {

    private final FeedbackCounterEvent feedbackCounterResetEvent = new FeedbackCounterEvent(RESET_COUNTER, this);

    private Response response;
    private String responseIdentifier;
    protected List<String> identifiers;

    protected Element moduleElement;

    @Inject
    protected Provider<ExListBox> listBoxProvider;

    protected ExListBox listBox;
    protected Panel container;

    protected boolean showingAnswers = false;
    protected boolean locked = false;
    protected boolean shuffle = false;

    protected boolean showEmptyOption = true;

    private EventsBus eventsBus;
    @Inject
    private SwypeBlocker swypeBlocker;

    @Inject
    private InlineChoiceStyleNameConstants styleNames;

    @Inject
    private PageScopeFactory scopeFactory;

    @Inject
    @PageScoped
    private ResponseSocket responseSocket;

    protected ExListBox.PopupPosition popupPosition = ExListBox.PopupPosition.ABOVE;

    protected IUniqueModule parentModule;

    @Override
    public void initModule(ModuleSocket moduleSocket, EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        super.initModule(moduleSocket);
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE_STARTED), this, scopeFactory.getCurrentPageScope());
    }

    @Override
    public String getIdentifier() {
        return responseIdentifier;
    }

    @Override
    public void addElement(Element element) {
        moduleElement = element;
    }

    @Override
    public void installViews(List<HasWidgets> placeholders) {
        responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier");
        response = responseSocket.getResponse(responseIdentifier);
        shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
        String userClass = XMLUtils.getAttributeAsString(moduleElement, "class");

        NodeList optionsNodes = moduleElement.getElementsByTagName("inlineChoice");
        List<Widget> baseBodies = new ArrayList<Widget>();
        List<Widget> popupBodies = new ArrayList<Widget>();
        List<String> identifiersTemp = new ArrayList<String>();

        for (int i = 0; i < optionsNodes.getLength(); i++) {
            InlineBodyGeneratorSocket inlineBodyGeneratorSocket = getModuleSocket().getInlineBodyGeneratorSocket();
            Widget baseBody = inlineBodyGeneratorSocket.generateInlineBody(optionsNodes.item(i));
            baseBodies.add(baseBody);
            Widget popupBody = inlineBodyGeneratorSocket.generateInlineBody(optionsNodes.item(i));
            popupBodies.add(popupBody);
            identifiersTemp.add(((Element) optionsNodes.item(i)).getAttribute("identifier"));
        }

        listBox = listBoxProvider.get();

        swypeBlocker.addBlockOnOpenCloseHandler(listBox);

        listBox.setPopupPosition(popupPosition);
        listBox.setChangeListener(this);

        if (showEmptyOption) {
            Widget emptyOptionInBody = new InlineHTML("&nbsp;");
            emptyOptionInBody.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP_OPTION_EMPTY());
            Widget emptyOptionInPopup = new InlineHTML("&nbsp;");
            emptyOptionInPopup.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP_OPTION_EMPTY());
            listBox.addOption(emptyOptionInBody, emptyOptionInPopup);
        }

        listBox.setSelectedIndex(getAnswerIndex());
        if (shuffle) {
            RandomizedSet<Integer> randomizedNodes = new RandomizedSet<Integer>();
            for (int i = 0; i < identifiersTemp.size(); i++) {
                randomizedNodes.push(i);
            }
            identifiers = new ArrayList<String>();
            while (randomizedNodes.hasMore()) {
                Integer currIndex = randomizedNodes.pull();
                identifiers.add(identifiersTemp.get(currIndex));
                listBox.addOption(baseBodies.get(currIndex), popupBodies.get(currIndex));
            }

        } else {
            identifiers = identifiersTemp;
            for (int i = 0; i < baseBodies.size() && i < popupBodies.size(); i++) {
                listBox.addOption(baseBodies.get(i), popupBodies.get(i));
            }
        }

        container = new FlowPanel();
        container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP());
        if (userClass != null && !"".equals(userClass)) {
            container.addStyleName(userClass);
        }
        container.add(listBox);

        placeholders.get(0)
                .add(container);
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

    @Override
    public void markAnswers(boolean mark) {
        if (mark) {
            listBox.setEnabled(false);
            int selectedIndex = listBox.getSelectedIndex();
            if (selectedIndex != getAnswerIndex()) {
                if (isResponseCorrect()) {
                    container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP_CORRECT());
                } else {
                    container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP_WRONG());
                }
            } else {
                container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP_NONE());
            }
        } else {
            container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP());
            listBox.setEnabled(true);
        }
    }

    private boolean isResponseCorrect() {
        ModuleSocket moduleSocket = getModuleSocket();
        List<Boolean> evaluateResponse = responseSocket.evaluateResponse(response);
        return evaluateResponse.get(0);
    }

    @Override
    public void showCorrectAnswers(boolean show) {
        if (show && !showingAnswers) {
            int correctAnswerIndex = identifiers.indexOf(response.correctAnswers.getSingleAnswer()) + getOptionIndex();
            listBox.setSelectedIndex(correctAnswerIndex);
        } else if (!show && showingAnswers) {
            int answerIndex = getAnswerIndex();
            if (!response.values.isEmpty()) {
                answerIndex = identifiers.indexOf(response.values.get(0)) + getOptionIndex();
            }
            listBox.setSelectedIndex(answerIndex);
        }
        showingAnswers = show;
    }

    private void updateStyleName(boolean add, String styleName) {
        if (add) {
            container.addStyleName(styleName);
        } else {
            container.removeStyleName(styleName);
        }
    }

    @Override
    public void lock(boolean lock) {
        locked = lock;
        listBox.setEnabled(!lock);
        updateStyleName(locked, styleNames.QP_TEXT_CHOICE_POPUP_DISABLED());

    }

    @Override
    public void reset() {
        eventsBus.fireEvent(feedbackCounterResetEvent);
        markAnswers(false);
        lock(false);
        listBox.setShowEmptyOptions(showEmptyOption);
        updateResponse(false, true);
        listBox.setEnabled(true);
        listBox.reset();
        container.setStyleName(styleNames.QP_TEXT_CHOICE_POPUP());
    }

    private int getOptionIndex() {
        return (showEmptyOption) ? 1 : 0;
    }

    private int getAnswerIndex() {
        return (showEmptyOption) ? 0 : -1;
    }

    @Override
    public JSONArray getState() {
        JSONArray jsonArr = new JSONArray();
        String stateString = "";
        if (listBox.getSelectedIndex() - getOptionIndex() >= 0 && !response.values.isEmpty()) {
            stateString = response.values.get(0);
        }
        jsonArr.set(0, new JSONString(stateString));
        return jsonArr;
    }

    @Override
    public void setState(JSONArray newState) {
        if (newState != null && newState.size() > 0 && newState.get(0)
                .isString() != null) {
            int index = identifiers.indexOf(newState.get(0)
                    .isString()
                    .stringValue());
            listBox.setSelectedIndex(index + getOptionIndex());
        }

        updateResponse(false);
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return ModuleJsSocketFactory.createSocketObject(this);
    }

    @Override
    public void setParentInlineModule(IUniqueModule module) {
        parentModule = module;
    }

    @Override
    public IUniqueModule getParentInlineModule() {
        return parentModule;
    }

    private void updateResponse(boolean userInteract) {
        updateResponse(userInteract, false);
    }

    private void updateResponse(boolean userInteract, boolean isReset) {
        if (!showingAnswers) {
            response.reset();
            if (listBox.getSelectedIndex() != getAnswerIndex()) {
                String lastValue = identifiers.get(listBox.getSelectedIndex() - getOptionIndex());
                response.add(lastValue);
            }
            StateChangedInteractionEvent stateChangeEvent = new StateChangedInteractionEvent(userInteract, isReset, parentModule);
            eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, stateChangeEvent), scopeFactory.getCurrentPageScope());
        }
    }

    @Override
    public void onChange() {
        if (!showingAnswers && !locked) {
            updateResponse(true);
        }

    }

    @Override
    public void setShowEmptyOption(boolean seo) {
        showEmptyOption = seo;
    }

    public void setPopupPosition(ExListBox.PopupPosition popupPosition) {
        this.popupPosition = popupPosition;
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.PAGE_CHANGE_STARTED) {
            listBox.hidePopup();
        }
    }
}
