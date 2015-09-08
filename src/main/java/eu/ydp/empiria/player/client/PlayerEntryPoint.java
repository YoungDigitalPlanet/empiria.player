package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.EntryPoint;

public class PlayerEntryPoint implements EntryPoint {

    @Override
    public void onModuleLoad() {
        final PlayerLoader playerLoader = PlayerGinjectorFactory.getPlayerGinjector().getPlayerLoader();
        playerLoader.load();
    }
}
