package eu.ydp.empiria.player.client.module.sourcelist.view;


public class ScormScrollPanel {
	public void lockScroll() {
		nativeLockScroll();
	}

	public void unlockScroll() {
		nativeUnlockScroll();
	}

	private native void nativeLockScroll() /*-{
		if (!!$wnd.empiriaLockScroll) {
			$wnd.empiriaLockScroll();
		}
	}-*/;

	private native void nativeUnlockScroll() /*-{
		if (!!$wnd.empiriaUnlockScroll) {
			$wnd.empiriaUnlockScroll();
		}
	}-*/;
}
