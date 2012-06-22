package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.util.PathUtil;

public class SoundExecutorSwfSimple implements SoundExecutor {

	private FlowPanel panelMain;
	
	public SoundExecutorSwfSimple(){
		panelMain = new FlowPanel();
		RootPanel.get().add(panelMain, -500, -500);
	}
	
	
	@Override
	public void play(String src) {
		panelMain.clear();
		FlowPanel panelEmbed = new FlowPanel();
		String id = Document.get().createUniqueId();
		panelEmbed.getElement().setId(id);
		panelMain.add(panelEmbed);	
		String installSrc = PathUtil.getPlayerPathDir() + "swfobject/expressInstall.swf"; 
		String swfSrc = PathUtil.getPlayerPathDir() + "flashAudioExecutorSimple/flashAudioExecutorSimple.swf";
		embedSwf(id, swfSrc, installSrc, src);
	}
	
	private native void embedSwf(String id, String swfSrc, String installSrc, String soundSrc)/*-{
		var flashvars = {source:soundSrc};
		$wnd.swfobject.embedSWF(swfSrc, id, 100, 100, "9", installSrc, flashvars);
		
	}-*/;

	@Override
	public void stop() {
	}

	@Override
	public void setSoundFinishedListener(SoundExecutorListener listener) {
	}

}
