package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.GWT;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;

public class PlayerGinjectorFactory {

	private static PlayerGinjector playerGinjector;

	public static PlayerGinjector getPlayerGinjector() {
		if (playerGinjector == null) {
			playerGinjector = GWT.create(PlayerGinjector.class);
		}

		return playerGinjector;
	}
}
