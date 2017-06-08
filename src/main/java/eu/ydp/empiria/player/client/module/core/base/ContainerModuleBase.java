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

package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;

public abstract class ContainerModuleBase extends ModuleBase implements ISingleViewWithBodyModule {

    private BodyGeneratorSocket bodyGenerator;

    @Override
    public void initModule(Element element, ModuleSocket ms, BodyGeneratorSocket bodyGenerator, EventsBus eventsBus) {
        this.bodyGenerator = bodyGenerator;
        initModule(ms, eventsBus);
        readAttributes(element);
        initModule(element);
        applyIdAndClassToView(getView());
    }

    @Override
    public List<IModule> getChildrenModules() {
        return getModuleSocket().getChildren(this);
    }

    @Override
    public List<HasParent> getNestedChildren() {
        return getModuleSocket().getNestedChildren(this);
    }

    protected BodyGeneratorSocket getBodyGenerator() {
        return bodyGenerator;
    }

    public abstract void initModule(Element element);
}
