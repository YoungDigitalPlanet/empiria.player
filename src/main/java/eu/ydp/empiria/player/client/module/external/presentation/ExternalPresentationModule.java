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

package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.Lockable;
import eu.ydp.empiria.player.client.module.core.flow.Resetable;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;
import eu.ydp.empiria.player.client.module.external.common.ExternalFolderNameProvider;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalPresentationModule extends SimpleModuleBase implements Lockable, Resetable, ExternalFolderNameProvider,
        StatefulModule {

    public static final String SOURCE_ATTRIBUTE = "src";
    private final ExternalPresentationPresenter presenter;
    private final ExternalPaths externalPaths;
    private final EmpiriaPaths empiriaPaths;
    private String presentationName;
    private ModuleTagName moduleTagName;

    @Inject
    public ExternalPresentationModule(ExternalPresentationPresenter presenter, @ModuleScoped ExternalPaths externalPaths,
                                      EmpiriaPaths empiriaPaths) {
        this.presenter = presenter;
        this.externalPaths = externalPaths;
        this.empiriaPaths = empiriaPaths;
    }

    @Override
    protected void initModule(Element element) {
        String tagName = element.getTagName();
        moduleTagName = ModuleTagName.getTag(tagName);
        presentationName = element.getAttribute(SOURCE_ATTRIBUTE);
        externalPaths.setExternalFolderNameProvider(this);
        presenter.init();
    }

    @Override
    public String getIdentifier() {
        return getModuleId();
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public JSONArray getState() {
        return presenter.getState();
    }

    @Override
    public void setState(JSONArray newState) {
        presenter.setState(newState);
    }

    @Override
    public void lock(boolean shouldLock) {
        if (shouldLock) {
            presenter.lock();
        } else {
            presenter.unlock();
        }
    }

    @Override
    public void reset() {
        presenter.reset();
    }

    @Override
    public String getExternalFolderName() {
        return presentationName;
    }

    @Override
    public String getExternalRelativePath(String file) {
        switch (moduleTagName) {
            case EXTERNAL_PRESENTATION:
                return empiriaPaths.getMediaFilePath(file);
            case EXTERNAL_COMMON_PRESENTATION:
                return empiriaPaths.getCommonsFilePath(file);
            default:
                throw new RuntimeException("Cannot create presentation module from " + moduleTagName);
        }
    }
}
