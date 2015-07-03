package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class TutorConfigJs extends JavaScriptObject {

    protected TutorConfigJs() {
    }

    public final native JsArray<TutorActionJs> getActions()/*-{
        return this.actions;
    }-*/;

    public final native JsArray<TutorJs> getTutors()/*-{
        return this.tutors;
    }-*/;
}
