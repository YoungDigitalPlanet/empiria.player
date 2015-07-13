package eu.ydp.empiria.player.client.controller.assets;

import com.google.gwt.user.client.Window;

public class URLOpenService {

    public void open(String path) {
        Window.open(path, "_blank", "");
    }
}
