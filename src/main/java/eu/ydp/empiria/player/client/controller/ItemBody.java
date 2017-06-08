/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.controller.variables.processor.global.IgnoredModules;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeClientType;
import eu.ydp.empiria.player.client.gin.factory.ModulesInstalatorFactory;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.ItemBodyModule;
import eu.ydp.empiria.player.client.module.core.base.*;
import eu.ydp.empiria.player.client.module.core.base.Group;
import eu.ydp.empiria.player.client.module.core.flow.LifecycleModule;
import eu.ydp.empiria.player.client.module.core.flow.OnModuleShowHandler;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemBody implements WidgetWorkflowListener {

    protected List<IModule> modules;

    protected ParenthoodManager parenthood;

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
    private final ModulesInstalatorFactory modulesInstalatorFactory;
    private final EventsBus eventsBus;

    @Inject
    public ItemBody(@Assisted DisplayContentOptions options, @Assisted ModuleSocket moduleSocket, ModuleHandlerManager moduleHandlerManager,
                    ModulesRegistrySocket modulesRegistrySocket, ModulesStateLoader modulesStateLoader,
                    IgnoredModules ignoredModules, PlayerWorkModeService playerWorkModeService, MathJaxNative mathJaxNative, ParenthoodManager parenthood,
                    ModulesInstalatorFactory modulesInstalatorFactory, EventsBus eventsBus) {

        this.moduleSocket = moduleSocket;
        this.options = options;
        this.modulesRegistrySocket = modulesRegistrySocket;
        this.moduleHandlerManager = moduleHandlerManager;
        this.modulesStateLoader = modulesStateLoader;
        this.mathJaxNative = mathJaxNative;

        this.parenthood = parenthood;

        this.ignoredModules = ignoredModules;
        this.playerWorkModeService = playerWorkModeService;
        this.modulesInstalatorFactory = modulesInstalatorFactory;
        this.eventsBus = eventsBus;
    }

    public Widget init(Element itemBodyElement) {

        ModulesInstalator modulesInstalator = modulesInstalatorFactory.createModulesInstalator(parenthood, modulesRegistrySocket, moduleSocket);
        BodyGenerator generator = new BodyGenerator(modulesInstalator, options);

        itemBodyModule = new ItemBodyModule();
        modulesInstalator.setInitialParent(itemBodyModule);
        itemBodyModule.initModule(itemBodyElement, moduleSocket, generator, eventsBus);

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
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onBodyLoad();
            }
        }

        attached = true;
        setState(stateAsync);
        mathJaxNative.renderMath();
    }

    @Override
    public void onUnload() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onBodyUnload();
            }
        }
    }

    public void setUp() {
        for (IModule currModule : modules) {
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onSetUp();
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
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onStart();
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
            if (currModule instanceof LifecycleModule) {
                ((LifecycleModule) currModule).onClose();
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
        Group currGroup = getGroupByGroupIdentifier(groupIdentifier);
        if (currGroup != null) {
            currGroup.markAnswers(mark);
        }
    }

    public void showCorrectAnswers(boolean show) {
        itemBodyModule.showCorrectAnswers(show);
    }

    public void showCorrectAnswers(boolean show, GroupIdentifier groupIdentifier) {
        Group currGroup = getGroupByGroupIdentifier(groupIdentifier);
        if (currGroup != null) {
            currGroup.showCorrectAnswers(show);
        }
    }

    public void reset() {
        itemBodyModule.reset();
    }

    public void reset(GroupIdentifier groupIdentifier) {
        Group currGroup = getGroupByGroupIdentifier(groupIdentifier);
        if (currGroup != null) {
            currGroup.reset();
        }
    }

    public void lock(boolean lo) {
        itemBodyModule.lock(lo);
    }

    public void lock(boolean lo, GroupIdentifier groupIdentifier) {
        Group currGroup = getGroupByGroupIdentifier(groupIdentifier);
        if (currGroup != null) {
            currGroup.lock(lo);
        }
    }

    private Group getGroupByGroupIdentifier(GroupIdentifier gi) {
        Group lastGroup = null;
        for (IModule currModule : modules) {
            if (currModule instanceof Group) {
                lastGroup = (Group) currModule;
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
