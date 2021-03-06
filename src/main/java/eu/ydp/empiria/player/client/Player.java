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

package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.version.Version;
import eu.ydp.empiria.player.client.view.ViewEngine;

/**
 * Main class with player API
 *
 */
public class Player {

    /**
     * Delivery engine do manage the assessment content
     */
    public DeliveryEngine deliveryEngine;

    private final IPlayerContainersAccessor accessor;

    {
        logVersion();
    }

    @Inject
    public Player(@Assisted String id, @Assisted JavaScriptObject jsObject, ViewEngine viewEngine, DeliveryEngine deliveryEngine,
                  IPlayerContainersAccessor accessor) {
        this.accessor = accessor;
        this.deliveryEngine = deliveryEngine;
        try {
            RootPanel.get(id);
        } catch (Exception e) {
        }
        RootPanel root = RootPanel.get(id);
        viewEngine.mountView(root);
        getAccessor().setPlayerContainer(root);

        deliveryEngine.init(jsObject);
    }

    public void loadExtension(JavaScriptObject extension) {
        deliveryEngine.loadExtension(extension);
    }

    public void loadExtension(String extension) {
        deliveryEngine.loadExtension(extension);
    }

    public void load(String url) {
        deliveryEngine.load(url);
    }

    public void load(XmlData assessmentData, XmlData[] itemsData) {
        deliveryEngine.load(assessmentData, itemsData);
    }

    private void logVersion() {
        String version = Version.getVersion();
        String versionMessage = "EmpiriaPlayer ver. " + version;
        log(versionMessage);
        System.out.println(versionMessage);
    }

    private native void log(String message)/*-{
      if (typeof console == 'object')
            console.log(message);
    }-*/;

    private IPlayerContainersAccessor getAccessor() {
        return accessor;
    }
}
