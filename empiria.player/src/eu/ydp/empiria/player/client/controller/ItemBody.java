package eu.ydp.empiria.player.client.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.body.ModuleEventsListener;
import eu.ydp.empiria.player.client.controller.body.ModulesInstalator;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.container.ItemBodyModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

public class ItemBody implements IActivity, IStateful, WidgetWorkflowListener {

	public List<IModule> modules;

	protected ModuleEventsListener moduleEventsListener;
	protected DisplayContentOptions options;
	protected ModuleSocket moduleSocket;
	protected ModulesRegistrySocket modulesRegistrySocket;

	private JSONArray stateAsync;
	private boolean attached = false;
	private boolean locked = false;
	private boolean markingAnswers = false;
	private boolean showingAnswers = false;

	// private Label traceLabel;

	public ItemBody(DisplayContentOptions options, ModuleSocket moduleSocket,
			final InteractionEventsListener interactionEventsListener, ModulesRegistrySocket modulesRegistrySocket) {

		this.moduleSocket = moduleSocket;
		this.options = options;
		this.modulesRegistrySocket = modulesRegistrySocket;

		moduleEventsListener = new ModuleEventsListener() {


			@Override
			public void onStateChanged(boolean procesFeedback, IUniqueModule sender) {
				interactionEventsListener
						.onStateChanged(new StateChangedInteractionEvent(
								procesFeedback, sender));
			}

			@Override
			public void onFeedbackSoundPlay(String url) {
				interactionEventsListener
						.onFeedbackSound(new FeedbackInteractionSoundEvent(url));
			}

			@Override
			public void onMediaSoundPlay(String url, MediaInteractionSoundEventCallback callback) {
				interactionEventsListener.onMediaSound(new MediaInteractionSoundEvent(url, callback));
			}

		};
		// traceLabel = new Label();
		// dom.appendChild(traceLabel.getElement());

	}

	public Widget init(Element itemBodyElement) {
		
		ModulesInstalator modulesInstalator = new ModulesInstalator(modulesRegistrySocket, moduleSocket, moduleEventsListener);
		BodyGenerator generator = new BodyGenerator(modulesInstalator, options);
		
		ItemBodyModule itemBodyModule = new ItemBodyModule(); 
		itemBodyModule.initModule(itemBodyElement, moduleSocket, generator);
		
		modules = modulesInstalator.installMultiViewUniqueModules();
		
		return itemBodyModule.getView();


	}

	// ------------------------- EVENTS --------------------------------

	public void onLoad() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule)
				((ILifecycleModule) currModule).onBodyLoad();
		}

		attached = true;

		setState(stateAsync);

		if (locked)
			markAnswers(true);
	}

	@Override
	public void onUnload() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule)
				((ILifecycleModule) currModule).onBodyUnload();
		}
	}
	
	public int getModuleCount() {
		return modules.size();
	}

	@Override
	public void markAnswers(boolean mark) {
		if (showingAnswers)
			showCorrectAnswers(false);
		markingAnswers = mark;
		for (IModule currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).markAnswers(mark);
		}

	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (markingAnswers)
			markAnswers(false);
		showingAnswers = show;
		for (IModule currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).showCorrectAnswers(show);
		}
	}

	@Override
	public void reset() {
		if (showingAnswers)
			showCorrectAnswers(false);
		if (markingAnswers)
			markAnswers(false);
		if (locked)
			lock(false);
		for (IModule currModule : modules) {
			if (currModule instanceof IActivity)
				((IActivity) currModule).reset();
		}

	}

	@Override
	public void lock(boolean l) {
		locked = l;
		for (IModule currModule : modules) {
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

		for (IModule currModule : modules) {
			if (currModule instanceof IStateful
					&& currModule instanceof IUniqueModule)
				states.put(((IUniqueModule) currModule).getIdentifier(),
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

					if (newState.isArray() != null  &&  newState.isArray().size() > 0){
						JSONObject stateObj = newState.isArray().get(0).isObject();
	
						for (int i = 0; i < modules.size(); i++) {
							if (modules.get(i) instanceof IStateful
									&& modules.get(i) instanceof IUniqueModule) {
								String curridentifier = ((IUniqueModule) modules
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
		for (IModule currModule : modules) {
			if (currModule instanceof eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket) {
				moduleSockets
						.add(((eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket) currModule));
			}
		}
		return moduleSockets
				.toArray(new eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[0]);
	}
}
