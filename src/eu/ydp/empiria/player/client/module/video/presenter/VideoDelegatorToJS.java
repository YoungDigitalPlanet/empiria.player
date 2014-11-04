package eu.ydp.empiria.player.client.module.video.presenter;

public class VideoDelegatorToJS {

	public void play(String playerId) {
		playNative(playerId);
	}

	private native void playNative(String id) /*-{
        if (typeof $wnd.empiriaExternalMediaPlay == 'function') {
            $wnd.empiriaExternalMediaPlay(id);
        }
    }-*/;
}

