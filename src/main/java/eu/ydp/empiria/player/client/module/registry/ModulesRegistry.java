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

package eu.ydp.empiria.player.client.module.registry;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.creator.ModuleCreator;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class ModulesRegistry implements ModulesRegistrySocket {

    protected final Map<String, ModuleCreator> moduleCreators;
    private final ModuleScopeStack moduleScopeStack;

    @Inject
    public ModulesRegistry(ModuleScopeStack moduleScopeStack) {
        this.moduleScopeStack = moduleScopeStack;
        moduleCreators = new HashMap<String, ModuleCreator>();
    }

    public void registerModuleCreator(String nodeName, ModuleCreator creator) {
        moduleCreators.put(nodeName, creator);
    }

    @Override
    public boolean isModuleSupported(String nodeName) {
        if (EmpiriaTagConstants.NAME_GAP.equals(nodeName)) {
            return true;
        } else {
            return moduleCreators.keySet().contains(nodeName);
        }
    }

    @Override
    public boolean isMultiViewModule(Element element) {
        if (EmpiriaTagConstants.NAME_GAP.equals(element.getTagName())) {
            String tagNameWithType = ModuleTagName.getTagNameWithType(element);
            return isMultiViewModule(tagNameWithType);
        }

        return isMultiViewModule(element.getTagName());
    }

    private boolean isMultiViewModule(String tagName){
        ModuleCreator currCreator = moduleCreators.get(tagName);
        if (currCreator != null) {
            return currCreator.isMultiViewModule();
        }
        return false;
    }

    @Override
    public boolean isInlineModule(String nodeName) {
        ModuleCreator currCreator = moduleCreators.get(nodeName);
        if (currCreator != null) {
            return currCreator.isInlineModule();
        }
        return false;
    }

    @Override
    public IModule createModule(Element element) {
        String nodeName = element.getNodeName();
        ModuleCreator currCreator = moduleCreators.get(nodeName);

        if ((currCreator == null) && (element.hasAttribute(EmpiriaTagConstants.ATTR_TYPE))) {
            nodeName = ModuleTagName.getTagNameWithType(element);
            currCreator = moduleCreators.get(nodeName);
        }

        ModuleCreationContext context = new ModuleCreationContext(element);
        moduleScopeStack.pushContext(context);
        IModule module = currCreator.createModule();
        moduleScopeStack.pop();

        return module;
    }
}
