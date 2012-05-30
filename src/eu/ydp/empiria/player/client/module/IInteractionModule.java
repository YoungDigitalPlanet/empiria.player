package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket;

public interface IInteractionModule extends IActivity, IStateful, ModuleInterferenceSocket, IUniqueModule, IMultiViewModule, ILifecycleModule {
}
