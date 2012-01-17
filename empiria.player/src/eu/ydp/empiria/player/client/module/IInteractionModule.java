package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public interface IInteractionModule extends IActivity, IStateful, ModuleInterferenceSocket, IUniqueModule, IMultiViewModule, ILifecycleModule {
}
