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

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.InlineBodyGeneratorFactory;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.HasParent;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.Group;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.json.js.YJsJsonFactory;

import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ItemModuleSocket implements ModuleSocket {

    private final Item item;
    private final YJsJsonConverter yJsJsonConverter;
    private final ModulesRegistrySocket modulesRegistrySocket;
    private final InlineBodyGeneratorFactory inlineBodyGeneratorFactory;

    private JSONArray state;
    private ItemBody itemBody;
    private InlineBodyGenerator inlineBodyGenerator;

    @Inject
    public ItemModuleSocket(@Assisted Item item, YJsJsonConverter yJsJsonConverter, ModulesRegistrySocket modulesRegistrySocket,
                            InlineBodyGeneratorFactory inlineBodyGeneratorFactory) {
        this.item = item;
        this.yJsJsonConverter = yJsJsonConverter;
        this.modulesRegistrySocket = modulesRegistrySocket;
        this.inlineBodyGeneratorFactory = inlineBodyGeneratorFactory;
    }

    public void init(ItemBody itemBody, JSONArray state) {
        this.itemBody = itemBody;
        this.state = state;
    }

    @Override
    public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
        if (inlineBodyGenerator == null) {
            inlineBodyGenerator = inlineBodyGeneratorFactory.createInlineBodyGenerator(modulesRegistrySocket, this, this.item.options, itemBody.getParenthood());
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
            if (currParent == null || currParent instanceof Group) {
                break;
            }
        }
        if (currParent != null) {
            return ((Group) currParent).getGroupIdentifier();
        }
        return new DefaultGroupIdentifier("");
    }

    @Override
    public List<IModule> getChildren(IModule parent) {
        return itemBody.getModuleChildren(parent);
    }

    @Override
    public List<HasParent> getNestedChildren(HasChildren parent) {
        return itemBody.getNestedChildren(parent);
    }

    @Override
    public List<HasChildren> getNestedParents(HasParent child) {
        return itemBody.getNestedParents(child);
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

    public void setState(JSONArray state) {
        this.state = state;
    }
}
