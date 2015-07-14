package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.Player;

public interface PlayerFactory {
    Player createPlayer(String id, JavaScriptObject jsObject);
}
