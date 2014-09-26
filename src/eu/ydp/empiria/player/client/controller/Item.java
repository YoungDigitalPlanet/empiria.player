package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.feedback.ModuleFeedbackProcessor;
import eu.ydp.empiria.player.client.controller.item.ItemExpressionParser;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.item.ItemXMLWrapper;
import eu.ydp.empiria.player.client.controller.variables.manager.BindableVariableManager;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.VariableProcessingAdapter;
import eu.ydp.empiria.player.client.controller.variables.processor.VariablesProcessingModulesInitializer;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FeedbackAutoMarkInterpreter;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;
import eu.ydp.empiria.player.client.module.workmode.WorkModeSwitcher;
import eu.ydp.empiria.player.client.view.item.ItemBodyView;

import java.util.Map;

public class Item implements IStateful, ItemInterferenceSocket {

	protected ItemBody itemBody;
	protected ItemBodyView itemBodyView;
	protected Panel scorePanel;
	protected DisplayContentOptions options;

	private final ModuleFeedbackProcessor moduleFeedbackProcessor;
	private final VariableManager<Response> responseManager;
	private final BindableVariableManager<Outcome> outcomeManager;
	private final ItemModuleSocket moduleSocket;
	private final String title;
	private final FlowActivityVariablesProcessor flowActivityVariablesProcessor;
	private final VariableProcessingAdapter variableProcessor;
	private final PlayerWorkModeService playerWorkModeService;

	private JSONArray state;

	@Inject
	public Item(@Assisted DisplayContentOptions options, @Assisted Map<String, Outcome> outcomeVariables, @Assisted JSONArray stateArray,
			ModuleFeedbackProcessor moduleFeedbackProcessor, FlowActivityVariablesProcessor flowActivityVariablesProcessor,
			@PageScoped VariableProcessingAdapter variableProcessingAdapter, VariablesProcessingModulesInitializer variablesProcessingModulesInitializer,
			ExpressionListBuilder expressionListBuilder, @PageScoped ItemResponseManager responseManager, ItemXMLWrapper xmlMapper,
			ItemExpressionParser expressionParser, AssessmentControllerFactory assessmentControllerFactory, PlayerWorkModeService playerWorkModeService) {

		this.options = options;
		this.responseManager = responseManager;
		this.moduleFeedbackProcessor = moduleFeedbackProcessor;
		this.flowActivityVariablesProcessor = flowActivityVariablesProcessor;
		this.variableProcessor = variableProcessingAdapter;
		this.playerWorkModeService = playerWorkModeService;

		Element itemBodyNode = xmlMapper.getItemBody();
		expressionParser.parseAndConnectExpressions();

		outcomeManager = new BindableVariableManager<Outcome>(outcomeVariables);
		new FeedbackAutoMarkInterpreter().interpretFeedbackAutoMark(itemBodyNode, responseManager.getVariablesMap());

		moduleSocket = assessmentControllerFactory.getItemModuleSocket(this);
		itemBody = assessmentControllerFactory.getItemBody(options, moduleSocket);
		moduleSocket.init(itemBody, state);
		itemBodyView = new ItemBodyView(itemBody);

		setState(stateArray);
		itemBodyView.init(itemBody.init(itemBodyNode));

		variablesProcessingModulesInitializer.initializeVariableProcessingModules(responseManager.getVariablesMap(), outcomeManager.getVariablesMap());

		Node rootNode = xmlMapper.getAssessmentItems()
								 .item(0);
		title = ((Element) rootNode).getAttribute("title");
		scorePanel = new FlowPanel();
		scorePanel.setStyleName("qp-feedback-hidden");
	}

	public void close() {
		itemBody.close();
	}

	public void process(boolean userInteract, boolean isReset) {
		process(userInteract, isReset, null);
	}

	public void process(boolean userInteract, boolean isReset, IUniqueModule sender) {
		ProcessingMode processingMode = findCorrectProcessingMode(userInteract, isReset);

		variableProcessor.processResponseVariables(responseManager.getVariablesMap(), outcomeManager.getVariablesMap(), processingMode);
		if (userInteract) {
			moduleFeedbackProcessor.processFeedbacks(outcomeManager.getVariablesMap(), sender);
		}
	}

	private ProcessingMode findCorrectProcessingMode(boolean userInteract, boolean isReset) {
		ProcessingMode processingMode;
		if (userInteract) {
			processingMode = ProcessingMode.USER_INTERACT;
		} else if (isReset) {
			processingMode = ProcessingMode.RESET;
		} else {
			processingMode = ProcessingMode.NOT_USER_INTERACT;
		}
		return processingMode;
	}

	public void handleFlowActivityEvent(JavaScriptObject event) {
		handleFlowActivityEvent(FlowActivityEvent.fromJsObject(event));
	}

	public void handleFlowActivityEvent(FlowActivityEvent event) {
		if (event == null) {
			return;
		}
		GroupIdentifier groupIdentifier;
		if (event.getGroupIdentifier() != null) {
			groupIdentifier = event.getGroupIdentifier();
		} else {
			groupIdentifier = new DefaultGroupIdentifier("");
		}

		if (event.getType() == FlowActivityEventType.CHECK) {
			checkGroup(groupIdentifier);
		} else if (event.getType() == FlowActivityEventType.CONTINUE) {
			continueGroup(groupIdentifier);
		} else if (event.getType() == FlowActivityEventType.SHOW_ANSWERS) {
			showAnswersGroup(groupIdentifier);
		} else if (event.getType() == FlowActivityEventType.RESET) {
			resetGroup(groupIdentifier);
		} else if (event.getType() == FlowActivityEventType.LOCK) {
			lockGroup(true, groupIdentifier);
		} else if (event.getType() == FlowActivityEventType.UNLOCK) {
			lockGroup(false, groupIdentifier);
		}

		flowActivityVariablesProcessor.processFlowActivityVariables(outcomeManager.getVariablesMap(), event);
	}

	public String getTitle() {
		return title;
	}

	public Panel getContentView() {
		return itemBodyView;
	}

	@Deprecated
	public Widget getScoreView() {
		return scorePanel;
	}

	// -------------------------- NAVIGATION -------------------------------

	public void checkItem() {
		itemBody.markAnswers(true);
		itemBody.lock(true);
	}

	public void checkGroup(GroupIdentifier gi) {
		itemBody.markAnswers(true, gi);
		itemBody.lock(true, gi);
	}

	public void continueItem() {
		itemBody.showCorrectAnswers(false);
		itemBody.markAnswers(false);
		itemBody.lock(false);
	}

	public void continueGroup(GroupIdentifier gi) {
		itemBody.showCorrectAnswers(false, gi);
		itemBody.markAnswers(false, gi);
		itemBody.lock(false, gi);
	}

	public void showAnswers() {
		itemBody.showCorrectAnswers(true);
		itemBody.lock(true);
	}

	public void showAnswersGroup(GroupIdentifier gi) {
		itemBody.showCorrectAnswers(true, gi);
		itemBody.lock(true, gi);
	}

	public void resetItem() {
		itemBody.reset();
		itemBody.lock(false);
	}

	public void resetGroup(GroupIdentifier gi) {
		itemBody.reset(gi);
		itemBody.lock(false, gi);
	}

	public void lockItem(boolean lock) {
		itemBody.lock(lock);
	}

	public void lockGroup(boolean lock, GroupIdentifier gi) {
		itemBody.lock(lock, gi);
	}

	@Override
	public JSONArray getState() {
		return itemBody.getState();
	}

	@Override
	public void setState(JSONArray newState) {
		state = newState;
		itemBody.setState(newState);
		moduleSocket.setState(newState);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createItemSocket(itemBody.getJsSocket());
	}

	private native JavaScriptObject createItemSocket(JavaScriptObject itemBodySocket)/*-{
        var socket = {};
        var instance = this;
        socket.reset = function () {
            instance.@eu.ydp.empiria.player.client.controller.Item::resetItem()();
        }
        socket.showAnswers = function () {
            instance.@eu.ydp.empiria.player.client.controller.Item::showAnswers()();
        }
        socket.lock = function () {
            instance.@eu.ydp.empiria.player.client.controller.Item::lockItem(Z)(true);
        }
        socket.unlock = function () {
            instance.@eu.ydp.empiria.player.client.controller.Item::lockItem(Z)(false);
        }
        socket.checkItem = function (value) {
            instance.@eu.ydp.empiria.player.client.controller.Item::checkItem()();
        }
        socket.continueItem = function (value) {
            instance.@eu.ydp.empiria.player.client.controller.Item::continueItem()();
        }
        socket.getOutcomeVariables = function () {
            return instance.@eu.ydp.empiria.player.client.controller.Item::getOutcomeVariablesJsSocket()();
        }
        socket.getResponseVariables = function () {
            return instance.@eu.ydp.empiria.player.client.controller.Item::getResponseVariablesJsSocket()();
        }
        socket.getItemBodySocket = function () {
            return itemBodySocket;
        }
        socket.handleFlowActivityEvent = function (event) {
            instance.@eu.ydp.empiria.player.client.controller.Item::handleFlowActivityEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
        }
        return socket;
    }-*/;

	private JavaScriptObject getOutcomeVariablesJsSocket() {
		return outcomeManager.getJsSocket();
	}

	private JavaScriptObject getResponseVariablesJsSocket() {
		return responseManager.getJsSocket();
	}

	@Override
	public eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[] getModuleSockets() {
		return itemBody.getModuleSockets();
	}

	public void setUp() {
		itemBody.setUp();
	}

	public void start() {
		itemBody.start();
		activateCorrectWorkMode();
	}

	private void activateCorrectWorkMode() {
		PlayerWorkMode currentWorkMode = playerWorkModeService.getCurrentWorkMode();
		WorkModeSwitcher currentWorkModeSwitcher = currentWorkMode.getWorkModeSwitcher();
		PlayerWorkMode previousWorkMode = playerWorkModeService.getPreviousWorkMode();
		WorkModeSwitcher previousWorkModeSwitcher = previousWorkMode.getWorkModeSwitcher();
		itemBody.disableWorkMode(previousWorkModeSwitcher);
		itemBody.enableWorkMode(currentWorkModeSwitcher);
	}

	public void setAssessmentParenthoodSocket(ParenthoodSocket parenthoodSocket) {
		itemBody.setUpperParenthoodSocket(parenthoodSocket);
	}

	/**
	 * Checks whether the item body contains at least one interactive module
	 *
	 * @return boolean
	 */
	public boolean hasInteractiveModules() {
		return itemBody.hasInteractiveModules();
	}

}