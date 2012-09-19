package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class JsMediaWrapper implements MediaWrapper<Widget> {

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		String json ="{\"playSupported\":true,\"stopSupported\":true,\"templateSupported\":true}";
		JsMediaAvaliableOptions options = (JsMediaAvaliableOptions) JSONParser.parseLenient(json).isObject().getJavaScriptObject();
		return options;
	}

	@Override
	public Widget getMediaObject() {
		return new FlowPanel();
	}

	@Override
	public String getMediaUniqId() {
		return null;
	}

	@Override
	public double getCurrentTime() {
		return 0;
	}

	@Override
	public double getDuration() {
		return 0;
	}

	@Override
	public boolean isMuted() {
		return false;
	}

	@Override
	public double getVolume() {
		return 1;
	}

	@Override
	public boolean canPlay() {
		return true;
	}

}
