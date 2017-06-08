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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public abstract class ModuleBase extends ParentedModuleBase {

    private String moduleId;
    private String moduleClass;
    private EventsBus eventsBus;

    @Override
    protected final void initModule(ModuleSocket moduleSocket) {
        super.initModule(moduleSocket);
    }

    public void initModule(ModuleSocket moduleSocket, EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        initModule(moduleSocket);
    }

    protected final void readAttributes(Element element) {

        String className = element.getAttribute("class");
        if (className != null && !"".equals(className)) {
            moduleClass = className;
        } else {
            moduleClass = "";
        }
        String id = element.getAttribute("id");
        if (id != null && !"".equals(id)) {
            moduleId = id;
        } else {
            moduleId = "";
        }
    }

    protected String getModuleId() {
        return moduleId;
    }

    protected String getModuleClass() {
        return moduleClass;
    }

    protected final void applyIdAndClassToView(Widget widget) {
        if (widget != null) {
            if (getModuleId() != null && !"".equals(getModuleId().trim()))
                widget.getElement().setId(getModuleId());
            if (getModuleClass() != null && !"".equals(getModuleClass().trim()))
                widget.addStyleName(getModuleClass());
        }
    }

    protected EventsBus getEventsBus() {
        return eventsBus;
    }
}
