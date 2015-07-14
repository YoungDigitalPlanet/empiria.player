package eu.ydp.empiria.player.client.controller.session.datasupplier;

import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;

public interface ItemSessionDataSupplier {
    ItemSessionDataSocket getItemSessionDataSocket(int index);

}
