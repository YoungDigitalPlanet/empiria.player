package eu.ydp.empiria.player.client.components.event;

import com.google.gwt.core.client.JavaScriptObject;

public class InputEventListenerJsWrapper {

    private InputEventListener listener;
    private JavaScriptObject javaScriptObject;

    public InputEventListenerJsWrapper(InputEventListener listener) {
        this.listener = listener;
        javaScriptObject = JavaScriptObject.createObject().cast();
        init();
    }

    public JavaScriptObject getJavaScriptObject() {
        return javaScriptObject;
    }

    private final native void init()/*-{
        var self = this;
        var javaScriptObject = self.@eu.ydp.empiria.player.client.components.event.InputEventListenerJsWrapper::javaScriptObject;
        javaScriptObject.onInput = function () {
            self.@eu.ydp.empiria.player.client.components.event.InputEventListenerJsWrapper::callListener()();

        }
    }-*/;

    private void callListener() {
        if (listener != null) {
            listener.onInput();
        }
    }
}
