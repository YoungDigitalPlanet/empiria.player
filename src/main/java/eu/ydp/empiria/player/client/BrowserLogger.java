package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.query.client.impl.ConsoleBrowser;

import java.util.Objects;

public class BrowserLogger {

    public static ConsoleBrowser consoleBrowser = new ConsoleBrowser();

    public static void log(Object object) {
        consoleBrowser.log(object);
    }

    public static native void logIsArray(JavaScriptObject state) /*-{
//        console.log(state.arr instanceof Array)
//        console.log(typeof state.arr, state.arr);
        console.log(state.arr.length);

    }-*/;
}
