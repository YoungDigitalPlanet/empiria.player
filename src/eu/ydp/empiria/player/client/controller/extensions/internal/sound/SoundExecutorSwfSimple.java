package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.media.LocalSwfMediaWrapper;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class SoundExecutorSwfSimple implements MediaExecutor<Widget> {

	private static final int PANEL_OFFSET = -500;
	private final FlowPanel panelMain;
	private final MediaWrapper<Widget> mediaWrapper;
	private BaseMediaConfiguration baseMediaConfiguration;

	@Inject
	public SoundExecutorSwfSimple(LocalSwfMediaWrapper localSwfMediaWrapper) {
		panelMain = new FlowPanel();
		RootPanel.get().add(panelMain, PANEL_OFFSET, PANEL_OFFSET);
		localSwfMediaWrapper.setMediaWidget(panelMain);
		mediaWrapper = localSwfMediaWrapper;
	}

	@Override
	@Deprecated
	public void play(String src) {
	}

	private void playFromSource(String src) {
		panelMain.clear();
		FlowPanel panelEmbed = new FlowPanel();
		String htmlId = Document.get().createUniqueId();
		panelEmbed.getElement().setId(htmlId);
		panelMain.add(panelEmbed);
		String installSrc = GWT.getModuleBaseURL() + "swfobject/expressInstall.swf";
		String swfSrc = GWT.getModuleBaseURL() + "flashAudioExecutorSimple/flashAudioExecutorSimple.swf";
		embedSwf(htmlId, swfSrc, installSrc, src);
	}

	private native void embedSwf(String id, String swfSrc, String installSrc, String soundSrc)/*-{
																								var flashvars = {
																								source : soundSrc
																								};
																								$wnd.swfobject.embedSWF(swfSrc, id, 100, 100, "9", installSrc,
																								flashvars);

																								}-*/;

	@Override
	public void stop() {
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
	}

	@Override
	public MediaWrapper<Widget> getMediaWrapper() {
		return mediaWrapper;
	}

	@Override
	public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
	}

	@Override
	public void init() {
	}

	@Override
	public void play() {
		if (sourceExists()) {
			String next = getSource();
			playFromSource(next);
		}
	}

	private boolean sourceExists() {
		return baseMediaConfiguration != null && !baseMediaConfiguration.getSources().isEmpty();
	}

	private String getSource() {
		return baseMediaConfiguration.getSources().keySet().iterator().next();
	}

	@Override
	public void pause() {
	}

	@Override
	public void setMuted(boolean mute) {
	}

	@Override
	public void setVolume(double volume) {
	}

	@Override
	public void setCurrentTime(double time) {
	}

	@Override
	public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
		this.baseMediaConfiguration = baseMediaConfiguration;
	}

	@Override
	public BaseMediaConfiguration getBaseMediaConfiguration() {
		return baseMediaConfiguration;
	}
}
