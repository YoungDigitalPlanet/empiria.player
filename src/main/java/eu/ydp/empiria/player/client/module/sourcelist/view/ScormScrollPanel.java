package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.inject.Singleton;

@Singleton
public class ScormScrollPanel {
    public void lockScroll() {
        nativeLockScroll();
    }

    public void unlockScroll() {
        nativeUnlockScroll();
    }

    private native void nativeLockScroll() /*-{
        if (typeof($wnd.empiriaLockScroll) === 'function') {
            $wnd.empiriaLockScroll();
        }
    }-*/;

    private native void nativeUnlockScroll() /*-{
        if (typeof($wnd.empiriaUnlockScroll) === 'function') {
            $wnd.empiriaUnlockScroll();
        }
    }-*/;
}
