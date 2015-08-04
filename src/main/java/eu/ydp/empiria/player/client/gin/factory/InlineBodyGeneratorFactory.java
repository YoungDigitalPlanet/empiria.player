package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.parenthood.ParenthoodManager;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;

public interface InlineBodyGeneratorFactory {

    InlineBodyGenerator createInlineBodyGenerator(ModulesRegistrySocket mrs, ModuleSocket moduleSocket, DisplayContentOptions options,
                                                  InteractionEventsListener interactionEventsListener, ParenthoodManager parenthood);
}
