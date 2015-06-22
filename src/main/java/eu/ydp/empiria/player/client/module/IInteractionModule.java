package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket;

public interface IInteractionModule extends ModuleInterferenceSocket, IMultiViewModule, ILifecycleModule, StatefulModule {
}
