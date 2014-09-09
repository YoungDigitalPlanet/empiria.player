package eu.ydp.empiria.player.client.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.BodyGenerator;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.body.ModulesInstalator;
import eu.ydp.empiria.player.client.controller.body.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.variables.processor.global.IgnoredModules;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.InteractionModuleBase;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.empiria.player.client.module.WorkModeClient;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.ItemBodyModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

public class ItemBody implements WidgetWorkflowListener {

	public List<IModule> modules;

	protected ParenthoodManager parenthood;

	protected InteractionEventsListener interactionEventsListener;
	protected DisplayContentOptions options;
	protected ModuleSocket moduleSocket;
	protected ModulesRegistrySocket modulesRegistrySocket;
	private final ModuleHandlerManager moduleHandlerManager;

	protected ItemBodyModule itemBodyModule;

	private JSONArray stateAsync;
	private boolean attached = false;
	private boolean stateIsLoaded = false;

	private final ModulesStateLoader modulesStateLoader;
	private final IgnoredModules ignoredModules;

	@Inject
	public ItemBody(@Assisted DisplayContentOptions options, @Assisted ModuleSocket moduleSocket, ModuleHandlerManager moduleHandlerManager,
			InteractionEventsListener interactionEventsListener, ModulesRegistrySocket modulesRegistrySocket, ModulesStateLoader modulesStateLoader,
			IgnoredModules isIgnoredModule) {

		this.moduleSocket = moduleSocket;
		this.options = options;
		this.modulesRegistrySocket = modulesRegistrySocket;
		this.moduleHandlerManager = moduleHandlerManager;
		this.modulesStateLoader = modulesStateLoader;

		parenthood = new ParenthoodManager();

		this.interactionEventsListener = interactionEventsListener;
		this.ignoredModules = isIgnoredModule;
	}

	public Widget init(Element itemBodyElement) {

		ModulesInstalator modulesInstalator = new ModulesInstalator(parenthood, modulesRegistrySocket, moduleSocket, interactionEventsListener);
		BodyGenerator generator = new BodyGenerator(modulesInstalator, options);

		itemBodyModule = new ItemBodyModule();
		modulesInstalator.setInitialParent(itemBodyModule);
		itemBodyModule.initModule(itemBodyElement, moduleSocket, interactionEventsListener, generator);

		modules = new ArrayList<IModule>();
		modules.add(itemBodyModule);
		modulesInstalator.installMultiViewNonuniuqeModules();
		modules.addAll(modulesInstalator.installMultiViewUniqueModules());
		modules.addAll(modulesInstalator.getInstalledSingleViewModules());

		for (IModule module : modules) {
			moduleHandlerManager.registerModule(module);
		}

		return itemBodyModule.getView();
	}

	// ------------------------- EVENTS --------------------------------

	@Override
	public void onLoad() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule) {
				((ILifecycleModule) currModule).onBodyLoad();
			}
		}

		attached = true;
		setState(stateAsync);
	}

	@Override
	public void onUnload() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule) {
				((ILifecycleModule) currModule).onBodyUnload();
			}
		}
	}

	public void setUp() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule) {
				((ILifecycleModule) currModule).onSetUp();
			}
			if (currModule instanceof InteractionModuleBase) {
				InteractionModuleBase moduleBase = (InteractionModuleBase) currModule;
				if (moduleBase.isIgnored()) {
					ignoredModules.addIgnoredID(moduleBase.getIdentifier());
				}
			}
		}
	}

	public void start() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule) {
				((ILifecycleModule) currModule).onStart();
			}
		}
	}

	public void enablePreviewMode() {
		for (IModule currModule : modules) {
			if (currModule instanceof WorkModeClient) {
				((WorkModeClient) currModule).enablePreviewMode();
			}
		}
	}

	public void close() {
		for (IModule currModule : modules) {
			if (currModule instanceof ILifecycleModule) {
				((ILifecycleModule) currModule).onClose();
			}
		}
	}

	public int getModuleCount() {
		return modules.size();
	}

	/**
	 * Checks whether the item body contains at least one interactive module
	 * 
	 * @return boolean
	 */
	public boolean hasInteractiveModules() {
		boolean foundInteractive = false;
		if (modules != null) {
			for (IModule currModule : modules) {
				if (currModule instanceof IInteractionModule) {
					foundInteractive = true;
					break;
				}
			}
		}
		return foundInteractive;
	}

	// ------------------------- ACTIVITY --------------------------------

	public void markAnswers(boolean mark) {
		itemBodyModule.markAnswers(mark);

	}

	public void markAnswers(boolean mark, GroupIdentifier groupIdentifier) {
		IGroup currGroup = getGroupByGroupIdentifier(groupIdentifier);
		if (currGroup != null) {
			currGroup.markAnswers(mark);
		}
	}

	public void showCorrectAnswers(boolean show) {
		itemBodyModule.showCorrectAnswers(show);
	}

	public void showCorrectAnswers(boolean show, GroupIdentifier groupIdentifier) {
		IGroup currGroup = getGroupByGroupIdentifier(groupIdentifier);
		if (currGroup != null) {
			currGroup.showCorrectAnswers(show);
		}
	}

	public void reset() {
		itemBodyModule.reset();
	}

	public void reset(GroupIdentifier groupIdentifier) {
		IGroup currGroup = getGroupByGroupIdentifier(groupIdentifier);
		if (currGroup != null) {
			currGroup.reset();
		}
	}

	public void lock(boolean lo) {
		itemBodyModule.lock(lo);
	}

	public void lock(boolean lo, GroupIdentifier groupIdentifier) {
		IGroup currGroup = getGroupByGroupIdentifier(groupIdentifier);
		if (currGroup != null) {
			currGroup.lock(lo);
		}
	}

	private IGroup getGroupByGroupIdentifier(GroupIdentifier gi) {
		IGroup lastGroup = null;
		for (IModule currModule : modules) {
			if (currModule instanceof IGroup) {
				lastGroup = (IGroup) currModule;
				if (lastGroup.getGroupIdentifier().equals(gi) || ("".equals(gi.getIdentifier()) && lastGroup instanceof ItemBodyModule)) {
					return lastGroup;
				}
			}
		}
		return lastGroup;
	}

	// ------------------------- STATEFUL --------------------------------

	public JSONArray getState() {

		JSONObject states = new JSONObject();

		for (IModule currModule : modules) {
			if (currModule instanceof IStateful && currModule instanceof IUniqueModule) {
				states.put(((IUniqueModule) currModule).getIdentifier(), ((IStateful) currModule).getState());
			}
		}

		JSONArray statesArr = new JSONArray();
		statesArr.set(0, states);

		return statesArr;
	}

	public void setState(JSONArray newState) {
		if (!attached) {
			stateAsync = newState;
		} else {
			setStateOnModulesIfNotSetYet(newState, modules);
		}
	}

	private void setStateOnModulesIfNotSetYet(JSONArray state, List<IModule> modules) {
		if (!stateIsLoaded) {
			modulesStateLoader.setState(state, modules);
			stateIsLoaded = true;
		}
	}

	public JavaScriptObject getJsSocket() {
		return createJsSocket();
	}

	private native JavaScriptObject createJsSocket()/*-{
		var socket = {};
		var instance = this;
		socket.getModuleSockets = function() {
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
				moduleSockets.add(((eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket) currModule));
			}
		}
		return moduleSockets.toArray(new eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[0]);
	}

	public HasChildren getModuleParent(IModule module) {
		return parenthood.getParent(module);
	}

	public List<IModule> getModuleChildren(IModule parent) {
		return parenthood.getChildren(parent);
	}

	public void setUpperParenthoodSocket(ParenthoodSocket parenthoodSocket) {
		parenthood.setUpperLevelParenthood(parenthoodSocket);
	}

	public ParenthoodManager getParenthood() {
		return parenthood;
	}
}
