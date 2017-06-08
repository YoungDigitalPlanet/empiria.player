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

package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.components.ModulePlaceholder;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.core.base.*;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.collections.StackMap;

import java.util.ArrayList;
import java.util.List;

public class ModulesInstalator implements ModulesInstalatorSocket {

    private ModulesRegistrySocket registry;
    private ModuleSocket moduleSocket;
    private ParenthoodGeneratorSocket parenthood;
    private List<IModule> singleViewModules;

    private StackMap<String, StackMap<Element, HasWidgets>> uniqueModulesMap = new StackMap<>();
    private StackMap<Element, StackMap<IModule, HasWidgets>> nonuniqueModulesMap = new StackMap<>();
    private StackMap<String, IModule> multiViewModulesMap = new StackMap<>();

    private FeedbackRegistry feedbackRegistry;
    private final EventsBus eventsBus;

    @Inject
    public ModulesInstalator(@Assisted ParenthoodGeneratorSocket pts, @Assisted ModulesRegistrySocket reg,
                             @Assisted ModuleSocket ms, FeedbackRegistry feedbackRegistry, EventsBus eventsBus) {
        this.registry = reg;
        this.moduleSocket = ms;
        this.parenthood = pts;
        this.feedbackRegistry = feedbackRegistry;
        this.eventsBus = eventsBus;
        singleViewModules = new ArrayList<>();
    }

    @Override
    public boolean isModuleSupported(String nodeName) {
        return registry.isModuleSupported(nodeName);
    }

    @Override
    public boolean isMultiViewModule(Element nodeName) {
        return registry.isMultiViewModule(nodeName);
    }

    @Override
    public void registerModuleView(Element element, HasWidgets parent) {
        String responseIdentifier = element.getAttribute("responseIdentifier");

        ModulePlaceholder placeholder = new ModulePlaceholder();
        parent.add(placeholder);

        if (responseIdentifier != null) {
            if (!uniqueModulesMap.containsKey(responseIdentifier)) {
                uniqueModulesMap.put(responseIdentifier, new StackMap<Element, HasWidgets>());
            }
            if (!multiViewModulesMap.containsKey(responseIdentifier)) {
                IModule currModule = registry.createModule(element);
                multiViewModulesMap.put(responseIdentifier, currModule);
                parenthood.addChild(currModule);
            }
            uniqueModulesMap.get(responseIdentifier).put(element, placeholder);
        } else {
            if (!nonuniqueModulesMap.containsKey(element)) {
                nonuniqueModulesMap.put(element, new StackMap<IModule, HasWidgets>());
            }

            IModule currModule = registry.createModule(element);
            nonuniqueModulesMap.get(element).put(currModule, placeholder);
            parenthood.addChild(currModule);
        }
    }

    @Override
    public void createSingleViewModule(Element element, HasWidgets parent, BodyGeneratorSocket bodyGeneratorSocket) {
        IModule module = registry.createModule(element);

        parenthood.addChild(module);
        registerModuleFeedbacks(module, element);

        if (module instanceof ISingleViewWithBodyModule) {
            parenthood.pushParent((ISingleViewWithBodyModule) module);
            ((ISingleViewWithBodyModule) module).initModule(element, moduleSocket, bodyGeneratorSocket, eventsBus);
            parenthood.popParent();
        } else if (module instanceof ISingleViewSimpleModule) {
            ((ISingleViewSimpleModule) module).initModule(element, moduleSocket, eventsBus);
        } else if (module instanceof IInlineModule) {
            ((IInlineModule) module).initModule(element, moduleSocket, eventsBus);
        }
        if (((ISingleViewModule) module).getView() instanceof Widget) {
            parent.add(((ISingleViewModule) module).getView());
        }

        singleViewModules.add(module);
    }

    private void registerModuleFeedbacks(IModule module, Element moduleElement) {
        feedbackRegistry.registerFeedbacks(module, moduleElement);
    }

    public void installMultiViewNonuniuqeModules() {
        for (Element currElement : nonuniqueModulesMap.getKeys()) {
            StackMap<IModule, HasWidgets> moduleMap = nonuniqueModulesMap.get(currElement);
            IModule module = moduleMap.getKeys().get(0);

            if (module instanceof IMultiViewModule) {
                IMultiViewModule multiViewModule = (IMultiViewModule) module;
                List<HasWidgets> placeholders = moduleMap.getValues();

                multiViewModule.initModule(moduleSocket, eventsBus);
                multiViewModule.addElement(currElement);
                multiViewModule.installViews(placeholders);
                registerModuleFeedbacks(module, currElement);
            }
        }
    }

    public List<IModule> installMultiViewUniqueModules() {

        List<IModule> modules = new ArrayList<IModule>();

        for (String responseIdentifier : uniqueModulesMap.getKeys()) {
            StackMap<Element, HasWidgets> currModuleMap = uniqueModulesMap.get(responseIdentifier);

            IModule currModule = multiViewModulesMap.get(responseIdentifier);
            if (currModule instanceof IMultiViewModule) {
                ((IMultiViewModule) currModule).initModule(moduleSocket, eventsBus);
            }

            for (Element currElement : currModuleMap.getKeys()) {
                registerModuleFeedbacks(currModule, currElement);

                if (currModule instanceof IMultiViewModule) {
                    ((IMultiViewModule) currModule).addElement(currElement);
                }
            }

            if (currModule instanceof IMultiViewModule) {
                ((IMultiViewModule) currModule).installViews(currModuleMap.getValues());
            }

            if (currModule != null) {
                modules.add(currModule);
            }

        }
        return modules;
    }

    public List<IModule> getInstalledSingleViewModules() {
        return singleViewModules;
    }

    public void setInitialParent(HasChildren parent) {
        parenthood.pushParent(parent);
    }

}
