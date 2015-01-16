package eu.ydp.empiria.player.client.module.identification;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import eu.ydp.gwtutil.client.event.factory.*;
import eu.ydp.gwtutil.client.xml.XMLUtils;
import java.util.*;

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
		choicesManager.lockAll();
	}

	@Override
	public void markAnswers(boolean mark) {
		CorrectAnswers correctAnswers = getResponse().correctAnswers;
		choicesManager.markAnswers(mark, correctAnswers);
	}

	@Override
	public void reset() {
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
