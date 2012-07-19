package eu.ydp.empiria.player.client.module.media.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.button.MediaController;

public class MediaDescriptionModule extends MediaController<MediaDescriptionModule> {
	private static ImgDescriptionModuleUiBinder uiBinder = GWT.create(ImgDescriptionModuleUiBinder.class);

	interface ImgDescriptionModuleUiBinder extends UiBinder<Widget, MediaDescriptionModule> {
	}

	@UiField
	protected FlowPanel text;

	public MediaDescriptionModule() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public MediaDescriptionModule getNewInstance() {
		return new MediaDescriptionModule();
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public void init() { //NOPMD

	}


}
