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

package eu.ydp.empiria.player.client.module.containers.group;

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.core.base.Group;
import eu.ydp.empiria.player.client.module.containers.BindingContainerModule;

public abstract class GroupModuleBase extends BindingContainerModule implements Group {

    protected GroupIdentifier groupIdentifier;
    private String moduleId;

    public GroupModuleBase() {
    }

    @Override
    protected String getModuleId() {
        if (super.getModuleId() == null || "".equals(super.getModuleId().trim())) {
            if (moduleId == null)
                moduleId = Document.get().createUniqueId();
            return moduleId;
        }
        return super.getModuleId();
    }

    @Override
    public void initModule(Element element) {
        super.initModule(element);
        groupIdentifier = new DefaultGroupIdentifier(getModuleId());
    }

    @Override
    public GroupIdentifier getGroupIdentifier() {
        return groupIdentifier;
    }

}
