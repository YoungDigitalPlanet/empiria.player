package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import com.google.gwt.core.client.JavaScriptObject;

class TextEditorDesktopOptions implements TextEditorOptions {

    @Override
    public JavaScriptObject getOptions() {
        return getDesktopOptions();
    }

    private native final JavaScriptObject getDesktopOptions() /*-{
        var options = {
            link: false,
            indent: false,
            outdent: false,
            unlink: false,
            remove: false,
            source: false,
            rule: false
        };
        return options;
    }-*/;
}
