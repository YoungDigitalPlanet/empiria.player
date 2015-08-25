package eu.ydp.empiria.player.client.module;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Singleton;

@Singleton
public class NativeEventWrapper {
    public void preventDefault(NativeEvent nativeEvent) {
        nativeEvent.preventDefault();
    }

}
