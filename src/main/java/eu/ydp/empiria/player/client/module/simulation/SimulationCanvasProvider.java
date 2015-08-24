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
