package eu.ydp.empiria.player.client.module.inlinechoice;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.AccessibleListBox;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEvent;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.module.core.base.ParentedModuleBase;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

import static eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackCounterEventTypes.RESET_COUNTER;

public class InlineChoiceDefaultController extends ParentedModuleBase implements InlineChoiceController {

    private final FeedbackCounterEvent feedbackCounterResetEvent = new FeedbackCounterEvent(RESET_COUNTER, this);

    private Response response;
    private String responseIdentifier;
    private AccessibleListBox listBox;
    private boolean shuffle = false;
    private String lastValue = null;
    private boolean showingAnswers = false;
    protected boolean showEmptyOption = true;
    private EventsBus eventsBus;
    protected Element moduleElement;
    @Inject
    @PageScoped
    private ResponseSocket responseSocket;
    @Inject
    private PageScopeFactory pageScopeFactory;

    protected Panel container;

    IUniqueModule parentModule;

    @Override
    public void initModule(ModuleSocket moduleSocket, EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        initModule(moduleSocket);
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

        listBox = new AccessibleListBox();
        if (shuffle) {
            initRandom(moduleElement);
        } else {
            init(moduleElement);
        }

        if (showEmptyOption) {
            listBox.setSelectedIndex(0);
        } else {
            listBox.setSelectedIndex(-1);
        }

        listBox.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                listBoxChanged();
            }
        });

        container = new FlowPanel();
        container.add(listBox);

        placeholders.get(0)
                .add(container);

        container.setStyleName("qp-text-choice");
        if (userClass != null && !"".equals(userClass)) {
            container.addStyleName(userClass);
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

    // ------------------------ INTERFACES ------------------------

    @Override
    public void lock(boolean lock) {
        listBox.setEnabled(!lock);
    }

    @Override
    public void markAnswers(boolean mark) {
        if (mark) {
            listBox.setEnabled(false);
            if (listBox.getSelectedIndex() != ((showEmptyOption) ? 0 : -1)) {
                if (response.isCorrectAnswer(lastValue)) {
                    container.setStyleName("qp-text-choice-correct");
                } else {
                    container.setStyleName("qp-text-choice-wrong");
                }
            } else {
                container.setStyleName("qp-text-choice-none");
            }
        } else {
            listBox.setEnabled(true);
            container.setStyleName("qp-text-choice");
        }
    }

    @Override
    public void reset() {
        eventsBus.fireEvent(feedbackCounterResetEvent);
        markAnswers(false);
        lock(false);
        listBox.setSelectedIndex(((showEmptyOption) ? 0 : -1));
        updateResponse(false, true);
        listBox.setEnabled(true);
        container.setStyleName("qp-text-choice");
    }

    @Override
    public void showCorrectAnswers(boolean show) {

        if (show && !showingAnswers) {
            showingAnswers = true;
            for (int i = 0; i < listBox.getItemCount(); i++) {
                if (listBox.getValue(i)
                        .equals(response.correctAnswers.getSingleAnswer())) {
                    listBox.setSelectedIndex(i);
                    break;
                }
            }
        } else if (!show && showingAnswers) {
            listBox.setSelectedIndex(-1);
            for (int i = 0; i < listBox.getItemCount(); i++) {
                if (listBox.getValue(i)
                        .compareTo((response.values.size() > 0) ? response.values.get(0) : "") == 0) {
                    listBox.setSelectedIndex(i);
                    break;
                }
            }
            showingAnswers = false;
        }
    }

    @Override
    public JavaScriptObject getJsSocket() {
        return ModuleJsSocketFactory.createSocketObject(this);
    }

    @Override
    public JSONArray getState() {

        JSONArray jsonArr = new JSONArray();

        String stateString = "";

        if (lastValue != null) {
            stateString = lastValue;
        }

        jsonArr.set(0, new JSONString(stateString));

        return jsonArr;
    }

    @Override
    public void setState(JSONArray newState) {

        String state = "";

        if (newState != null && newState.size() > 0 && newState.get(0)
                .isString() != null) {
            state = newState.get(0)
                    .isString()
                    .stringValue();
            lastValue = null;
        }

        for (int i = 0; i < listBox.getItemCount(); i++) {
            if (listBox.getValue(i)
                    .compareTo(state) == 0) {
                listBox.setSelectedIndex(i);
                break;
            }
        }
        updateResponse(false);
    }

    private void init(Element inlineChoiceElement) {
        NodeList nodes = inlineChoiceElement.getChildNodes();

        // Add no answer as first option
        if (showEmptyOption) {
            listBox.addItem(" ");
        }

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i)
                    .getNodeName()
                    .compareTo("inlineChoice") == 0) {
                Element choiceElement = (Element) nodes.item(i);
                listBox.addItem(XMLUtils.getText(choiceElement), XMLUtils.getAttributeAsString(choiceElement, "identifier"));
            }
        }
    }

    private void initRandom(Element inlineChoiceElement) {
        RandomizedSet<Element> randomizedNodes = new RandomizedSet<Element>();
        NodeList nodes = inlineChoiceElement.getChildNodes();

        // Add no answer as first option
        if (showEmptyOption) {
            listBox.addItem(" ");
        }

        // Add nodes to temporary list
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i)
                    .getNodeName()
                    .compareTo("inlineChoice") == 0) {
                randomizedNodes.push((Element) nodes.item(i));
            }
        }

        while (randomizedNodes.hasMore()) {
            Element choiceElement = randomizedNodes.pull();
            listBox.addItem(XMLUtils.getText(choiceElement), XMLUtils.getAttributeAsString(choiceElement, "identifier"));
        }

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
        if (showingAnswers) {
            return;
        }

        if (lastValue != null) {
            response.remove(lastValue);
        }

        lastValue = listBox.getValue(listBox.getSelectedIndex());
        if (lastValue == null) {
            lastValue = "";
        }
        response.add(lastValue);
        CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
        eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, false, parentModule)),
                currentPageScope);
    }

    @Override
    public String getIdentifier() {
        return responseIdentifier;
    }

    protected void listBoxChanged() {
        updateResponse(true);
    }

    @Override
    public void setShowEmptyOption(boolean seo) {
        showEmptyOption = seo;
    }
}
