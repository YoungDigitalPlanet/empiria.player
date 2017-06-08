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

package eu.ydp.empiria.player.client.module.simulation;

import com.google.common.base.Optional;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Element;
import com.google.inject.Singleton;
import eu.ydp.gwtcreatejs.client.loader.CreateJsContent;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;

@Singleton
public class SimulationCanvasProvider {

    public Optional<Canvas> getSimulationCanvas(CreateJsLoader createJsLoader) {

        if (createJsLoader == null || createJsLoader.getContent() == null) {
            return Optional.<Canvas>absent();
        }

        CreateJsContent content = createJsLoader.getContent();

        return Optional.fromNullable(content.getCanvas());

    }

    public Optional<Element> getSimulationCanvasElement(CreateJsLoader createJsLoader) {
        Optional<Canvas> simulationCanvas = getSimulationCanvas(createJsLoader);
        if (!simulationCanvas.isPresent()) {
            return Optional.<Element>absent();

        }
        Canvas canvas = simulationCanvas.get();
        return Optional.fromNullable(canvas.getElement());
    }
}
