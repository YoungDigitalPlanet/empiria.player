package eu.ydp.empiria.player.client.controller.assets;

public class AssetOpenJSDelegator {

	public native boolean empiriaExternalLinkSupported()/*-{
		var supported = false;
		if (!!$wnd.empiriaExternalLinkSupported) {
			supported = $wnd.empiriaExternalLinkSupported();
		}
		return supported;
	}-*/;

	public native void empiriaExternalLinkOpen(String path)/*-{
		if (!!$wnd.empiriaExternalLinkOpen) {
			$wnd.empiriaExternalLinkOpen(path);
		}
	}-*/;
}
