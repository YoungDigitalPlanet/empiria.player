package eu.ydp.empiria.player.client.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
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
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.InlineContainerStylesExtractor;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.view.item.ItemBodyView;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.json.js.YJsJsonFactory;

public class Item implements IStateful, ItemInterferenceSocket {

	protected ItemBody itemBody;
	protected ItemBodyView itemBodyView;
	protected Panel scorePanel;

	private final ModuleFeedbackProcessor moduleFeedbackProcessor;
	private final VariableManager<Response> responseManager;
	private final BindableVariableManager<Outcome> outcomeManager;

	protected ModulesRegistrySocket modulesRegistrySocket;
	protected DisplayContentOptions options;

	private final String title;

	private final InteractionEventsListener interactionEventsListener;
	private final FlowActivityVariablesProcessor flowActivityVariablesProcessor;

	private final VariableProcessingAdapter variableProcessor;
	private final YJsJsonConverter yJsJsonConverter;
	private JSONArray state;

	@Inject
	public Item(@Assisted DisplayContentOptions options, @Assisted InteractionEventsListener interactionEventsListener, @Assisted ModulesRegistrySocket mrs,
			@Assisted Map<String, Outcome> outcomeVariables, @Assisted ModuleHandlerManager moduleHandlerManager, @Assisted JSONArray stateArray,
			ModuleFeedbackProcessor moduleFeedbackProcessor, FlowActivityVariablesProcessor flowActivityVariablesProcessor,
			@PageScoped VariableProcessingAdapter variableProcessingAdapter, VariablesProcessingModulesInitializer variablesProcessingModulesInitializer,
			YJsJsonConverter yJsJsonConverter, ExpressionListBuilder expressionListBuilder, @PageScoped ItemResponseManager responseManager,
			ItemXMLWrapper xmlMapper, ItemExpressionParser expressionParser) {

		this.modulesRegistrySocket = mrs;
		this.options = options;
		this.yJsJsonConverter = yJsJsonConverter;
		this.responseManager = responseManager;
		this.moduleFeedbackProcessor = moduleFeedbackProcessor;
		this.flowActivityVariablesProcessor = flowActivityVariablesProcessor;
		this.variableProcessor = variableProcessingAdapter;

		Element itemBodyNode = xmlMapper.getItemBody();
		expressionParser.parseAndConnectExpressions();

		this.interactionEventsListener = interactionEventsListener;
		outcomeManager = new BindableVariableManager<Outcome>(outcomeVariables);
		new FeedbackAutoMarkInterpreter().interpretFeedbackAutoMark(itemBodyNode, responseManager.getVariablesMap());

		itemBody = new ItemBody(options, moduleSocket, interactionEventsListener, modulesRegistrySocket, moduleHandlerManager);
		itemBodyView = new ItemBodyView(itemBody);

		setState(stateArray);
		itemBodyView.init(itemBody.init(itemBodyNode));

		variablesProcessingModulesInitializer.initializeVariableProcessingModules(responseManager.getVariablesMap(), outcomeManager.getVariablesMap());

		Node rootNode = xmlMapper.getAssessmentItems().item(0);
		title = ((Element) rootNode).getAttribute("title");
		scorePanel = new FlowPanel();
		scorePanel.setStyleName("qp-feedback-hidden");

	}

	/**
	 * Inner class for module socket implementation
	 */
	private final ModuleSocket moduleSocket = new ModuleSocket() {

		private InlineBodyGenerator inlineBodyGenerator;

		@Override
		public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
			if (inlineBodyGenerator == null) {
				inlineBodyGenerator = new InlineBodyGenerator(modulesRegistrySocket, this, options, interactionEventsListener, itemBody.getParenthood());
			}
			return inlineBodyGenerator;
		}

		@Override
		public HasChildren getParent(IModule module) {
			return itemBody.getModuleParent(module);
		}

		@Override
		public GroupIdentifier getParentGroupIdentifier(IModule module) {
			IModule currParent = module;

			while (true) {
				currParent = getParent(currParent);
				if (currParent == null || currParent instanceof IGroup) {
					break;
				}
			}
			if (currParent != null) {
				return ((IGroup) currParent).getGroupIdentifier();
			}
			return new DefaultGroupIdentifier("");
		}

		@Override
		public List<IModule> getChildren(IModule parent) {
			return itemBody.getModuleChildren(parent);
		}

		@Override
		public Stack<HasChildren> getParentsHierarchy(IModule module) {
			Stack<HasChildren> hierarchy = new Stack<HasChildren>();
			HasChildren currParent = getParent(module);
			while (currParent != null) {
				hierarchy.push(currParent);
				currParent = getParent(currParent);
			}
			return hierarchy;
		}

		@Override
		public Set<InlineFormattingContainerType> getInlineFormattingTags(IModule module) {
			InlineContainerStylesExtractor inlineContainerHelper = new InlineContainerStylesExtractor();
			return inlineContainerHelper.getInlineStyles(module);
		}

		@Override
		public YJsonArray getStateById(String id) {
			JSONValue object = state.get(0);
			if (object != null) {
				JSONValue stateValue = object.isObject().get(id);
				JSONArray stateArray = stateValue.isArray();
				return yJsJsonConverter.toYJson(stateArray);
			}
			return YJsJsonFactory.createArray();
		}
	};

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

	@Deprecated
	public int getModulesCount() {
		return itemBody.getModuleCount();
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
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return createItemSocket(itemBody.getJsSocket());
	}

	private native JavaScriptObject createItemSocket(JavaScriptObject itemBodySocket)/*-{
		var socket = {};
		var instance = this;
		socket.reset = function() {
			instance.@eu.ydp.empiria.player.client.controller.Item::resetItem()();
		}
		socket.showAnswers = function() {
			instance.@eu.ydp.empiria.player.client.controller.Item::showAnswers()();
		}
		socket.lock = function() {
			instance.@eu.ydp.empiria.player.client.controller.Item::lockItem(Z)(true);
		}
		socket.unlock = function() {
			instance.@eu.ydp.empiria.player.client.controller.Item::lockItem(Z)(false);
		}
		socket.checkItem = function(value) {
			instance.@eu.ydp.empiria.player.client.controller.Item::checkItem()();
		}
		socket.continueItem = function(value) {
			instance.@eu.ydp.empiria.player.client.controller.Item::continueItem()();
		}
		socket.getOutcomeVariables = function() {
			return instance.@eu.ydp.empiria.player.client.controller.Item::getOutcomeVariablesJsSocket()();
		}
		socket.getResponseVariables = function() {
			return instance.@eu.ydp.empiria.player.client.controller.Item::getResponseVariablesJsSocket()();
		}
		socket.getItemBodySocket = function() {
			return itemBodySocket;
		}
		socket.handleFlowActivityEvent = function(event) {
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