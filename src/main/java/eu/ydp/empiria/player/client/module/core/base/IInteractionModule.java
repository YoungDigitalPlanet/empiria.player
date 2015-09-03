package eu.ydp.empiria.player.client.module.core.base;

import eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket;
import eu.ydp.empiria.player.client.module.core.flow.ILifecycleModule;
import eu.ydp.empiria.player.client.module.core.flow.StatefulModule;

public interface IInteractionModule extends ModuleInterferenceSocket, IMultiViewModule, ILifecycleModule, StatefulModule {
}
