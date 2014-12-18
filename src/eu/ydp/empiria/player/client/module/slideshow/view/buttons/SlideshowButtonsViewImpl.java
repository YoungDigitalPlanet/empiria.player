package eu.ydp.empiria.player.client.module.slideshow.view.buttons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.*;

public class SlideshowButtonsViewImpl extends Composite implements SlideshowButtonsView {
	@UiTemplate("SlideshowButtonsView.ui.xml")
	interface SlideshowButtonsUiBinder extends UiBinder<Widget, SlideshowButtonsViewImpl> {
	};

	private final SlideshowButtonsUiBinder uiBinder = GWT.create(SlideshowButtonsUiBinder.class);

	@UiField
	protected FlowPanel buttonsPanel;

	@UiField
	protected TwoStateButton playButton;

	@UiField
	protected PushButton stopButton;

	@UiField
	protected PushButton nextButton;

	@UiField
	protected PushButton previousButton;

	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	@Inject
	private StyleNameConstants styleNameConstants;

	private Presenter presenter;

	public SlideshowButtonsViewImpl() {
		uiBinder.createAndBindUi(this);
		addEventsHandlers();
	}

	@UiFactory
	protected TwoStateButton init() {
		return new TwoStateButton(styleNameConstants.QP_SLIDESHOW_BUTTON_PLAY(), styleNameConstants.QP_SLIDESHOW_BUTTON_PAUSE());
	}

	@Override
	public void setEnabledNextButton(boolean enabled) {
		nextButton.setEnabled(enabled);
	}

	@Override
	public void setEnabledPreviousButton(boolean enabled) {
		previousButton.setEnabled(enabled);
	}

	@Override
	public void setPlayButtonDown(boolean down) {
		playButton.setStateDown(down);
	}

	@Override
	public Widget asWidget() {
		return buttonsPanel.asWidget();
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	private void addEventsHandlers() {
		userInteractionHandlerFactory.applyUserClickHandler(createOnPlayClickCommand(), playButton);
		userInteractionHandlerFactory.applyUserClickHandler(createOnStopClickCommand(), stopButton);
		userInteractionHandlerFactory.applyUserClickHandler(createOnPreviousClickCommand(), previousButton);
		userInteractionHandlerFactory.applyUserClickHandler(createOnNextClickCommand(), nextButton);
	}

	private Command createOnStopClickCommand() {
		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				playButton.setStateDown(false);
				presenter.executeStop();
			}
		};
	}

	private Command createOnNextClickCommand() {
		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.executeNext();
			}
		};
	}

	private Command createOnPreviousClickCommand() {
		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				presenter.executePrevious();
			}
		};
	}

	private Command createOnPlayClickCommand() {
		return new Command() {

			@Override
			public void execute(NativeEvent event) {
				if (playButton.isStateDown()) {
					presenter.executePlay();
				} else {
					presenter.executePause();
				}
			}
		};
	}
}
