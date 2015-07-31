package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.module.registry.InlineBodyGeneratorSocketProvider;
import eu.ydp.gwtutil.client.json.YJsonArray;

public interface ModuleSocket extends InlineBodyGeneratorSocketProvider, ParenthoodSocket {

    YJsonArray getStateById(String identifier);

}
