package eu.ydp.empiria.player.client.module.img.picture.player.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerPresenter;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.*;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class PicturePlayerViewImpl extends Composite implements PicturePlayerView {

	private PicturePlayerUiBinder uiBinder = GWT.create(PicturePlayerUiBinder.class);
	private StyleNameConstants styleNameConstants;
	private PicturePlayerPresenter presenter;
	private UserInteractionHandlerFactory handlerFactory;

	@UiTemplate(value = "PicturePlayerView.ui.xml") interface PicturePlayerUiBinder extends UiBinder<Widget, PicturePlayerViewImpl> {

	}
	@UiField
	protected Image image;
	@UiField
	protected FlowPanel container;

	@Inject
	public PicturePlayerViewImpl(StyleNameConstants styleNameConstants, UserInteractionHandlerFactory handlerFactory) {
		this.styleNameConstants = styleNameConstants;
		this.handlerFactory = handlerFactory;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addFullscreenButton() {
		CustomPushButton fullScreenButton = new CustomPushButton();
		fullScreenButton.setStyleName(styleNameConstants.QP_MEDIA_FULLSCREEN_BUTTON());

		handlerFactory.applyUserClickHandler(createOnOpenFullscreenCommand(), fullScreenButton);
		container.add(fullScreenButton);
	}

	@Override public void setPresenter(PicturePlayerPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setImage(String title, String url) {
		image.setAltText(title);
		image.setUrl(url);
	}

	private Command createOnOpenFullscreenCommand() {
		return new Command() {
			@Override
			public void execute(NativeEvent nativeEvent) {
				presenter.openFullscreen();
			}
		};
	}
}
