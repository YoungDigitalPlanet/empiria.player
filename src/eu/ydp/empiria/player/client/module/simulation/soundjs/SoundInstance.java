package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.core.client.JavaScriptObject;

public final class SoundInstance extends JavaScriptObject {

	protected SoundInstance() {
	}

	public native int getUniqueId() /*-{
		return this.uniqueId;
	}-*/;

}
