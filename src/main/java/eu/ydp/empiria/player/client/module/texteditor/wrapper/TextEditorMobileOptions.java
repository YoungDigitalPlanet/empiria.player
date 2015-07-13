package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import com.google.gwt.core.client.JavaScriptObject;

class TextEditorMobileOptions implements TextEditorOptions {

    @Override
    public JavaScriptObject getOptions() {
        return getMobileOptions();
    }

    private native final JavaScriptObject getMobileOptions() /*-{
        var options = {
            fsize: false,
            format: false,
            color: false,
            b: false,
            i: false,
            u: false,
            ol: false,
            ul: false,
            sup: false,
            sub: false,
            left: false,
            center: false,
            right: false,
            strike: false,

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
