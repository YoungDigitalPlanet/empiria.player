package eu.ydp.empiria.player.client.module.media.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

/**
 * Modul wyswietlajacy tytul
 * 
 * @author plelakowski
 * 
 */
public class MediaTitleModule extends AbstractMediaController<MediaTitleModule> {
	private static ImgTitleModuleUiBinder uiBinder = GWT.create(ImgTitleModuleUiBinder.class);
	protected StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();

	interface ImgTitleModuleUiBinder extends UiBinder<Widget, MediaTitleModule> {
	}

	@UiField
	protected FlowPanel text;

	public MediaTitleModule() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public MediaTitleModule getNewInstance() {
		return new MediaTitleModule();
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public void init() {// NOPMD

	}

	@Override
	public void setStyleNames() {
		if (isInFullScreen()) {
			text.removeStyleName(styleNames.QP_MEDIA_TITLE());
			text.addStyleName(styleNames.QP_MEDIA_TITLE() + FULL_SCREEN_SUFFIX);
		}
	}

}
