	package eu.ydp.empiria.player.client.controller;

	import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
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
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackManager;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.controller.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.manager.BindableVariableManager;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessorTemplate;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.item.ItemBodyView;

	public class Item implements IStateful, ItemInterferenceSocket {

		protected ItemBody itemBody;

		protected ItemBodyView itemBodyView;

		protected Panel scorePanel;

		protected VariableProcessor variableProcessor;

		private FeedbackManager feedbackManager;

		private VariableManager<Response> responseManager;

		private BindableVariableManager<Outcome> outcomeManager;

		private StyleLinkDeclaration styleDeclaration;

		private StyleSocket styleSocket;

		protected ModulesRegistrySocket modulesRegistrySocket;

		protected DisplayContentOptions options;

		private String title;

		private XmlData xmlData;

		private InteractionEventsListener interactionEventsListener;

		@Inject
		public Item(@Assisted XmlData data, @Assisted DisplayContentOptions options, 
					@Assisted InteractionEventsListener interactionEventsListener, @Assisted StyleSocket ss, 
					@Assisted ModulesRegistrySocket mrs, @Assisted Map<String, Outcome> outcomeVariables, 
					@Assisted ModuleHandlerManager moduleHandlerManager, AssessmentControllerFactory controllerFactory) {

			this.modulesRegistrySocket = mrs;
			this.options = options;

			xmlData = data;

			styleSocket = ss;

			Node rootNode = xmlData.getDocument().getElementsByTagName("assessmentItem").item(0);
			Node itemBodyNode = xmlData.getDocument().getElementsByTagName("itemBody").item(0);

			variableProcessor = VariableProcessorTemplate.fromNode(xmlData.getDocument().getElementsByTagName("variableProcessing"));
			
			feedbackManager = controllerFactory.getFeedbackManager(xmlData.getDocument().getElementsByTagName("modalFeedback"), xmlData.getBaseURL(), 
																	moduleSocket, interactionEventsListener);

			responseManager = new VariableManager<Response>(xmlData.getDocument().getElementsByTagName("responseDeclaration"), new IVariableCreator<Response>() {
				@Override
				public Response createVariable(Node node) {
					return new Response(node);
				}
			});
			this.interactionEventsListener = interactionEventsListener;
			outcomeManager = new BindableVariableManager<Outcome>(outcomeVariables);

			variableProcessor.ensureVariables(responseManager.getVariablesMap(), outcomeManager.getVariablesMap());

			styleDeclaration = new StyleLinkDeclaration(xmlData.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());

			VariableProcessor.interpretFeedbackAutoMark(itemBodyNode, responseManager.getVariablesMap());

			itemBody = new ItemBody(options, moduleSocket, interactionEventsListener, modulesRegistrySocket, moduleHandlerManager);

			itemBodyView = new ItemBodyView(itemBody);

			itemBodyView.init(itemBody.init((Element) itemBodyNode));

			feedbackManager.setBodyView(itemBodyView);

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
			public eu.ydp.empiria.player.client.controller.variables.objects.response.Response getResponse(String id) {
				return responseManager.getVariable(id);
			}

			@Override
			public List<Boolean> evaluateResponse(Response response) {
				return variableProcessor.evaluateAnswer(response);
			}

			@Override
			public void addInlineFeedback(InlineFeedback inlineFeedback) {
				feedbackManager.addInlineFeedback(inlineFeedback);
			}

			@Override
			public Map<String, String> getStyles(Element element) {
				return (styleSocket != null) ? styleSocket.getStyles(element) : new HashMap<String, String>();
			}

			@Override
			public Map<String,String> getOrgStyles(Element element) {
				return (styleSocket != null) ? styleSocket.getOrgStyles(element) : new HashMap<String, String>();
			};

			@Override
			public void setCurrentPages(PageReference pr) {
				if (styleSocket != null) {
					styleSocket.setCurrentPages(pr);
				}
			}

			@Override
			public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
				if (inlineBodyGenerator == null) {
					inlineBodyGenerator = new InlineBodyGenerator(modulesRegistrySocket, this, options, interactionEventsListener);
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
			public boolean hasInteractiveModules() {
				return Item.this.hasInteractiveModules();
			}

		};

		public void close() {
			feedbackManager.hideAllInlineFeedbacks();
			itemBody.close();
		}

		public void process(boolean userInteract) {
			process(userInteract, null);
		}

		public void process(boolean userInteract, IUniqueModule sender) {
			variableProcessor.processResponseVariables(responseManager.getVariablesMap(), outcomeManager.getVariablesMap(), userInteract);
			if (userInteract) {
				feedbackManager.process(responseManager.getVariablesMap(), outcomeManager.getVariablesMap(), sender);
			}
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

			variableProcessor.processFlowActivityVariables(outcomeManager.getVariablesMap(), event);
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
		public Widget getFeedbackView() {
			return feedbackManager.getModalFeedbackView();
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