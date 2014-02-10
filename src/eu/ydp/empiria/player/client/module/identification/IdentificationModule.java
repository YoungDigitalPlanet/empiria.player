package eu.ydp.empiria.player.client.module.identification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.InteractionModuleBase;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class IdentificationModule extends InteractionModuleBase implements Factory<IdentificationModule> {

	private int maxSelections;
	private boolean locked = false;
	private boolean showingCorrectAnswers = false;

	@Inject
	private Provider<IdentificationModule> identificationModuleProvider;
	@Inject
	private UserInteractionHandlerFactory interactionHandlerFactory;
	@Inject
	private IdentificationModuleFactory identificationModuleFactory;

	private List<SelectableChoice> options;

	protected List<Element> multiViewElements = new ArrayList<Element>();

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
	public void addElement(Element element) {
		multiViewElements.add(element);
	}

	Predicate<SelectableChoice> onlySelectedOptions = new Predicate<SelectableChoice>() {

		@Override
		public boolean apply(SelectableChoice choice) {
			return choice.getSelected();
		}
	};

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		setResponseFromElement(multiViewElements.get(0));
		options = new ArrayList<SelectableChoice>();
		String userClass = "";

		for (int e = 0; e < multiViewElements.size() && e < placeholders.size(); e++) {

			Element element = multiViewElements.get(e);
			HasWidgets currPlaceholder = placeholders.get(e);
			ArrayList<SelectableChoice> currOptions = new ArrayList<SelectableChoice>();

			boolean shuffle = false;
			String separatorString = "/";

			if (e == 0) {

				shuffle = XMLUtils.getAttributeAsBoolean(element, "shuffle");
				maxSelections = XMLUtils.getAttributeAsInt(element, "maxSelections");
				userClass = XMLUtils.getAttributeAsString(element, "class");

				separatorString = XMLUtils.getAttributeAsString(element, "separator");
			}

			RandomizedSet<SelectableChoice> optionsSet = new RandomizedSet<SelectableChoice>();
			ArrayList<Boolean> fixeds = new ArrayList<Boolean>();
			NodeList optionNodes = element.getElementsByTagName("simpleChoice");
			for (int i = 0; i < optionNodes.getLength(); i++) {
				final SelectableChoice selectableChoice = createSelectableChoice((Element) optionNodes.item(i), getModuleSocket()
						.getInlineBodyGeneratorSocket());
				currOptions.add(selectableChoice);
				if (shuffle && !XMLUtils.getAttributeAsBoolean((Element) optionNodes.item(i), "fixed")) {
					optionsSet.push(selectableChoice);
					fixeds.add(false);
				} else {
					fixeds.add(true);
				}
				addClickHandler(selectableChoice);
			}

			FlowPanel panel = new FlowPanel();
			panel.setStyleName("qp-identification-module");
			if (userClass != null && !"".equals(userClass)) {
				panel.addStyleName(userClass);
			}
			for (int i = 0; i < currOptions.size(); i++) {
				if (fixeds.get(i)) {
					panel.add(currOptions.get(i));
				} else {
					panel.add(optionsSet.pull());
				}
				if (i != currOptions.size() - 1) {
					Label sep = new Label(separatorString);
					sep.setStyleName("qp-identification-separator");
					panel.add(sep);
				}
			}

			currPlaceholder.add(panel);
			options.addAll(currOptions);
		}
	}

	private SelectableChoice createSelectableChoice(Element item, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		String identifier = XMLUtils.getAttributeAsString(item, "identifier");
		Widget contentWidget = inlineBodyGeneratorSocket.generateInlineBody(item);
		SelectableChoice selectableChoice = identificationModuleFactory.createSelectableChoice(identifier, contentWidget);
		return selectableChoice;
	}

	private void addClickHandler(final SelectableChoice selectableChoice) {
		Command clickCommand = createClickCommand(selectableChoice);
		EventHandlerProxy userClickHandler = interactionHandlerFactory.createUserClickHandler(clickCommand);
		userClickHandler.apply(selectableChoice);
	}

	private Command createClickCommand(final SelectableChoice selectableChoice) {
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
		for (SelectableChoice option : options) {
			option.setLocked(locked);
		}
	}

	@Override
	public void markAnswers(boolean mark) {
		CorrectAnswers correctAnswers = getResponse().correctAnswers;
		for (SelectableChoice currSC : options) {
			boolean correct = correctAnswers.containsAnswer(currSC.getIdentifier());
			currSC.markAnswers(mark, correct);
		}
	}

	@Override
	public void reset() {
		markAnswers(false);
		lock(false);
		clearSelections();
		updateResponse(false, true);
	}

	private void clearSelections() {
		for (SelectableChoice choice : options) {
			choice.setSelected(false);
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show) {
			selectCorrectAnswers();
		} else {
			restoreView();
		}
		showingCorrectAnswers = show;
	}

	private void restoreView() {
		List<String> values = getResponse().values;
		for (SelectableChoice sc : options) {
			boolean select = values.contains(sc.getIdentifier());
			sc.setSelected(select);
		}
	}

	private void selectCorrectAnswers() {
		CorrectAnswers correctAnswers = getResponse().correctAnswers;
		for (SelectableChoice sc : options) {
			boolean select = correctAnswers.containsAnswer(sc.getIdentifier());
			sc.setSelected(select);
		}
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (int i = 0; i < options.size(); i++) {
			arr.set(i, JSONBoolean.getInstance(getResponse().values.contains(options.get(i).getIdentifier())));
		}

		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		for (int i = 0; i < options.size(); i++) {
			JSONValue value = newState.get(i);
			if (value != null) {
				options.get(i).setSelected(value.isBoolean().booleanValue());
			}
		}
		updateResponse(false);
	}

	protected void onChoiceClick(SelectableChoice selectableChoice) {
		if (!locked) {
			selectableChoice.setSelected(!selectableChoice.getSelected());
			Collection<SelectableChoice> selectedOptions = Collections2.filter(options, onlySelectedOptions);
			int currSelectionsCount = selectedOptions.size();
			if (currSelectionsCount > maxSelections) {
				for (SelectableChoice choice : selectedOptions) {
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
			ArrayList<String> currResponseValues = new ArrayList<String>();
			Collection<SelectableChoice> selectedOptions = Collections2.filter(options, onlySelectedOptions);
			for (SelectableChoice currSC : selectedOptions) {
				currResponseValues.add(currSC.getIdentifier());
			}

			if (!getResponse().compare(currResponseValues) || !getResponse().isInitialized()) {
				getResponse().set(currResponseValues);
				fireStateChanged(userInteract, isReset);
			}
		}
	}

	@Override
	public IdentificationModule getNewInstance() {
		return identificationModuleProvider.get();
	}

}
