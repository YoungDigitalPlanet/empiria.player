package eu.ydp.empiria.player.client.module.sourcelist.view;

public class ScormScrollPanel {
	public void lockScroll(){
		nativeLockScroll();
	}
	
	public void unlockScroll(){
		nativeUnlockScroll();
	}
	
	private native void nativeLockScroll() /*-{
		$wnd.lockScroll();
	}-*/;

	
	
	private native void nativeUnlockScroll() /*-{
		$wnd.unlockScroll();
	}-*/;
}
