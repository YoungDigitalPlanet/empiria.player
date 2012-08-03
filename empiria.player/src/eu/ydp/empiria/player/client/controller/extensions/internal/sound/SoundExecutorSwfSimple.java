package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.PathUtil;

public class SoundExecutorSwfSimple implements SoundExecutor<Widget> {

	private final FlowPanel panelMain;

	public SoundExecutorSwfSimple(){
		panelMain = new FlowPanel();
		RootPanel.get().add(panelMain, -500, -500);
	}


	@Override
	public void play(String src) {
		panelMain.clear();
		FlowPanel panelEmbed = new FlowPanel();
		String htmlId = Document.get().createUniqueId();
		panelEmbed.getElement().setId(htmlId);
		panelMain.add(panelEmbed);
		String installSrc = PathUtil.getPlayerPathDir() + "swfobject/expressInstall.swf";
		String swfSrc = PathUtil.getPlayerPathDir() + "flashAudioExecutorSimple/flashAudioExecutorSimple.swf";
		embedSwf(htmlId, swfSrc, installSrc, src);
	}

	private native void embedSwf(String objectId, String swfSrc, String installSrc, String soundSrc)/*-{
		var flashvars = {source:soundSrc};
		$wnd.swfobject.embedSWF(swfSrc, id, 100, 100, "9", installSrc, flashvars);

	}-*/;

	@Override
	public void stop() {//NOPMD
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {//NOPMD
	}


	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return null;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {//NOPMD

	}

	@Override
	public void init() {//NOPMD

	}


	@Override
	public void play() {//NOPMD

	}


	@Override
	public void pause() {//NOPMD

	}


	@Override
	public void setMuted(boolean mute) {//NOPMD

	}


	@Override
	public void setVolume(double volume) {//NOPMD

	}


	@Override
	public void setCurrentTime(double time) {//NOPMD

	}


	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {//NOPMD

	}

}
