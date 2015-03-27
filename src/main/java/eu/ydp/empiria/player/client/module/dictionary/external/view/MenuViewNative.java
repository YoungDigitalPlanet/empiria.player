package eu.ydp.empiria.player.client.module.dictionary.external.view;

public class MenuViewNative {

    public native void exitJs()/*-{
        if (typeof $wnd.dictionaryExit == 'function') {
            $wnd.dictionaryExit();
        }
    }-*/;
}
