package eu.ydp.empiria.player.client.controller;

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
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.variables.processor.global.IgnoredModules;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.ItemBodyModule;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemBody implements WidgetWorkflowListener {

    protected List<IModule> modules;

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
    private MathJaxNative mathJaxNative;
    private final IgnoredModules ignoredModules;
    private final PlayerWorkModeService playerWorkModeService;

    @Inject
    public ItemBody(@Assisted DisplayContentOptions options, @Assisted ModuleSocket moduleSocket, ModuleHandlerManager moduleHandlerManager,
                    InteractionEventsListener interactionEventsListener, ModulesRegistrySocket modulesRegistrySocket, ModulesStateLoader modulesStateLoader,
                    IgnoredModules ignoredModules, PlayerWorkModeService playerWorkModeService, MathJaxNative mathJaxNative, ParenthoodManager parenthood) {

        this.moduleSocket = moduleSocket;
        this.options = options;
        this.modulesRegistrySocket = modulesRegistrySocket;
        this.moduleHandlerManager = moduleHandlerManager;
        this.modulesStateLoader = modulesStateLoader;
        this.mathJaxNative = mathJaxNative;

        this.parenthood = parenthood;

        this.interactionEventsListener = interactionEventsListener;
        this.ignoredModules = ignoredModules;
        this.playerWorkModeService = playerWorkModeService;
    }

    public Widget init(Element itemBodyElement) {

        ModulesInstalator modulesInstalator = new ModulesInstalator(parenthood, modulesRegistrySocket, moduleSocket, interactionEventsListener);
        BodyGenerator generator = new BodyGenerator(modulesInstalator, options);

        itemBodyModule = new ItemBodyModule();
        modulesInstalator.setInitialParent(itemBodyModule);
        itemBodyModule.initModule(itemBodyElement, moduleSocket, generator);

        modules = new ArrayList<>();
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
        mathJaxNative.renderMath();
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
                processIgnoredModule(moduleBase);
            }
            if (currModule instanceof WorkModeClientType) {
                playerWorkModeService.registerModule((WorkModeClientType) currModule);
            }
        }
    }

    private void processIgnoredModule(InteractionModuleBase moduleBase) {
        if (moduleBase.isIgnored()) {
            ignoredModules.addIgnoredID(moduleBase.getIdentifier());
        }
    }

    public void start() {
        for (IModule currModule : modules) {
            if (currModule instanceof ILifecycleModule) {
                ((ILifecycleModule) currModule).onStart();
            }
        }
    }

    public void onShow() {
        for (IModule currModule : modules) {
            if (currModule instanceof OnModuleShowHandler) {
                ((OnModuleShowHandler) currModule).onShow();
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
                if (lastGroup.getGroupIdentifier()
                        .equals(gi) || ("".equals(gi.getIdentifier()) && lastGroup instanceof ItemBodyModule)) {
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
            if (currModule instanceof StatefulModule) {
                StatefulModule statefulModule = (StatefulModule) currModule;
                states.put(statefulModule.getIdentifier(), statefulModule.getState());
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
        socket.getModuleSockets = function () {
            return instance.@eu.ydp.empiria.player.client.controller.ItemBody::getModuleJsSockets()();
        };
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

    public List<HasParent> getNestedChildren(HasChildren parent) {
        return parenthood.getNestedChildren(parent);
    }

    public List<HasChildren> getNestedParents(HasParent child) {
        return parenthood.getNestedParents(child);
    }
}
