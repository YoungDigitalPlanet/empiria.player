package eu.ydp.empiria.player.client.module.drawing.command;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ClearAllDrawCommand implements DrawCommand {
    @Inject
    @ModuleScoped
    private DrawCanvas view;

    @Override
    public void execute() {
        view.clear();
    }

}
