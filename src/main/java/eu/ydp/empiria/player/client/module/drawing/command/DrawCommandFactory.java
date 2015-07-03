package eu.ydp.empiria.player.client.module.drawing.command;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedLazyProvider;

public class DrawCommandFactory {
    @Inject
    private ModuleScopedLazyProvider<ClearAllDrawCommand> clearAllComand;

    public DrawCommand createCommand(DrawCommandType type) {
        if (type == DrawCommandType.CLEAR_ALL) {
            return clearAllComand.get();
        }
        return null;
    }
}
