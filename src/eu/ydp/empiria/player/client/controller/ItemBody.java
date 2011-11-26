package eu.ydp.empiria.player.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackSoundInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventHandlerInfo;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventManager;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventsListener;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.model.IModuleCreator;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IBrowserEventHandler;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModuleEventsListener;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;
import eu.ydp.empiria.player.client.module.IUniqueComponent;
import eu.ydp.empiria.player.client.module.ModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.mathexpr.MathJaxProcessor;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;
import eu.ydp.empiria.player.client.util.xml.XMLConverter;

public class ItemBody implements IActivity, IStateful, InternalEventsListener,
		WidgetWorkflowListener {

	public Vector<Widget> modules = new Vector<Widget>();
	public Vector<IUnattachedComponent> unattachedComponents = new Vector<IUnattachedComponent>();

	public InternalEventManager eventManager;

	protected IModuleEventsListener moduleEventsListener;
	protected DisplayContentOptions options;
	protected ModuleSocket moduleSocket;

	private JSONArray stateAsync;
	private boolean attached = false;
	private boolean locked = false;
	private boolean markingAnswers = false;
	private boolean showingAnswers = false;

	// private Label traceLabel;

	public ItemBody(DisplayContentOptions options, ModuleSocket moduleSocket,
			final InteractionEventsListener interactionEventsListener) {

		eventManager = new InternalEventManager();

		this.moduleSocket = moduleSocket;
		this.options = options;

		moduleEventsListener = new IModuleEventsListener() {

			@Override
			public void onTouchStart(com.google.gwt.dom.client.Element target,
					int pageX, int pageY) {
				onInternalEvent(new InternalEvent(target, Event.ONMOUSEDOWN,
						pageX, pageY));

			}

			@Override
			public void onTouchMove(com.google.gwt.dom.client.Element target,
					int pageX, int pageY) {
				onInternalEvent(new InternalEvent(target, Event.ONMOUSEMOVE,
						pageX, pageY));

			}

			@Override
			public void onTouchEnd(com.google.gwt.dom.client.Element target) {
				onInternalEvent(new InternalEvent(target, Event.ONMOUSEUP));

			}

			@Override
			public void onStateChanged(boolean procesFeedback,
					IInteractionModule sender) {
				interactionEventsListener
						.onStateChanged(new StateChangedInteractionEvent(
								procesFeedback, sender));
			}

			@Override
			public void onSoundPlay(String url) {
				interactionEventsListener
						.onFeedback(new FeedbackSoundInteractionEvent(url));
			}

		};

		ModuleFactory.isSupported("x");

		// traceLabel = new Label();
		// dom.appendChild(traceLabel.getElement());

	}

	public com.google.gwt.dom.client.Element getView(Node itemBodyNode) {

		com.google.gwt.dom.client.Element dom = XMLConverter.getDOM(
				(Element) itemBodyNode, moduleSocket, moduleEventsListener,
				new IModuleCreator() {

					@Override
					public boolean isSupported(String name) {
						return isModuleSupported(name);
					}

					@Override
					public com.google.gwt.dom.client.Element createModule(
							Element element, ModuleSocket moduleSocket,
							IModuleEventsListener moduleEventsListener) {
						Widget widget = ModuleFactory.createWidget(element,
								moduleSocket, moduleEventsListener);

						// if (widget instanceof IInteractionModule)
						addModule(widget, moduleSocket);

						return widget.getElement();
					}
				}, options);

		return dom;

	}

	protected boolean isModuleSupported(String name) {
		return ModuleFactory.isSupported(name);

	}

	protected void addModule(Widget newModule, ResponseSocket responseSocket) {

		if (newModule instanceof IInteractionModule)
			modules.add(newModule);

		if (newModule instanceof IUnattachedComponent)
			unattachedComponents.add((IUnattachedComponent) newModule);

		if (newModule instanceof IBrowserEventHandler) {

			Vector<InternalEventTrigger> triggers = ((IBrowserEventHandler) newModule)
					.getTriggers();

			if (triggers != null)
				for (InternalEventTrigger t : triggers)
					eventManager.register(new InternalEventHandlerInfo(
							((IBrowserEventHandler) newModule), t));
		}

		if (newModule instanceof IInteractionModule) {
			responseSocket.getResponse(
					((IInteractionModule) newModule).getIdentifier())
					.setModuleAdded();
		}

	}

	// ------------------------- EVENTS --------------------------------

	public void onInternalEvent(InternalEvent event) {

		com.google.gwt.dom.client.Element element = event
				.getEventTargetElement();

		/*
		 * @SuppressWarnings("unused") String tmpId = element.getId(); int
		 * evtType= event.getTypeInt(); if (tmpId.length() > 1)
		 * traceLabel.setText(tmpId); else traceLabel.setText("---");
		 * 
		 * if (evtType == Event.ONMOUSEDOWN){
		 * traceLabel.setText(traceLabel.getText() + " DOWN"); } else if
		 * (evtType == Event.ONMOUSEUP){ traceLabel.setText(traceLabel.getText()
		 * + " UP"); } else if (evtType == Event.ONMOUSEMOVE){
		 * traceLabel.setText(traceLabel.getText() + " MOVE"); }
		 */
		Vector<IBrowserEventHandler> handlers = eventManager.getHandlers(
				element.getId(), event.getTypeInt());

		if (handlers.size() > 0) {
			int dasd = 343;
			dasd++;
		}

		for (IBrowserEventHandler m : handlers)
			m.handleEvent(element.getId(), event);

	}

	public void onLoad() {
		if (unattachedComponents.size() > 0)
			for (IUnattachedComponent mod : unattachedComponents) {
				if (mod instanceof IUnattachedComponent)
					((IUnattachedComponent) mod).onOwnerAttached();
			}

		attached = true;

		setState(stateAsync);

		if (locked)
			markAnswers(true);

		MathJaxProcessor.pushAll();

	}

	public int getModuleCount() {
		return modules.size();
	}

	@Override
	public void markAnswers(boolean mark) {
		if (showingAnswers)
			return;
		markingAnswers = mark;
		for (Widget currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).markAnswers(mark);
		}

	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (markingAnswers)
			return;
		showingAnswers = show;
		for (Widget currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).showCorrectAnswers(show);
		}
	}

	@Override
	public void reset() {
		for (Widget currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).reset();
		}

	}

	@Override
	public void lock(boolean l) {
		locked = l;
		for (Widget currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).lock(l);
		}

	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public JSONArray getState() {

		JSONObject states = new JSONObject();

		for (Widget currModule : modules) {
			if (currModule instanceof IStateful
					&& currModule instanceof IUniqueComponent)
				states.put(((IUniqueComponent) currModule).getIdentifier(),
						((IStateful) currModule).getState());
		}

		JSONArray statesArr = new JSONArray();
		statesArr.set(0, states);

		return statesArr;
	}

	@Override
	public void setState(JSONArray newState) {
		if (!attached) {
			stateAsync = newState;
		} else {
			if (newState instanceof JSONArray) {

				try {

					JSONObject stateObj = newState.isArray().get(0).isObject();

					for (int i = 0; i < modules.size(); i++) {
						if (modules.get(i) instanceof IStateful
								&& modules.get(i) instanceof IUniqueComponent) {
							String curridentifier = ((IUniqueComponent) modules
									.get(i)).getIdentifier();

							if (curridentifier != null && curridentifier != "") {

								if (stateObj.containsKey(curridentifier)) {
									JSONValue currState = stateObj
											.get(curridentifier);
									if (currState != null
											&& currState.isArray() != null)
										((IStateful) modules.get(i))
												.setState(currState.isArray());
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	public JavaScriptObject getJsSocket() {
		return createJsSocket();
	}

	private native JavaScriptObject createJsSocket()/*-{
		var socket = {};
		var instance = this; 
		socket.getModuleSockets = function(){
			return instance.@eu.ydp.empiria.player.client.controller.ItemBody::getModuleJsSockets()();
		}
		return socket;
	}-*/;

	private JavaScriptObject getModuleJsSockets() {
		eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[] moduleSockets = getModuleSockets();
		JsArray<JavaScriptObject> moduleSocketsJs = JSArrayUtils.createArray(0);
		for (int i = 0; i < moduleSockets.length; i++) {
			moduleSocketsJs.set(i, moduleSockets[i].getJsSocket());
		}
		return moduleSocketsJs;
	}

	public eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[] getModuleSockets() {
		List<eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket> moduleSockets = new ArrayList<eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket>();
		for (Widget currModule : modules) {
			if (currModule instanceof eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket) {
				moduleSockets
						.add(((eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket) currModule));
			}
		}
		return moduleSockets
				.toArray(new eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[0]);
	}
}
