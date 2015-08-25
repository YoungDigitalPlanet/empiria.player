package eu.ydp.empiria.player.client.module.slideshow.view.buttons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowStyleNameConstants;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowButtonsPresenter;

public class SlideshowButtonsViewImpl extends Composite implements SlideshowButtonsView {
    @UiTemplate("SlideshowButtonsView.ui.xml")
    interface SlideshowButtonsUiBinder extends UiBinder<Widget, SlideshowButtonsViewImpl> {
    }

    private final SlideshowButtonsUiBinder uiBinder = GWT.create(SlideshowButtonsUiBinder.class);

    @UiField
    protected FlowPanel buttonsPanel;
    @UiField
    protected TwoStateButton playPauseButton;
    @UiField
    protected PushButton stopButton;
    @UiField
    protected PushButton nextButton;
    @UiField
    protected PushButton previousButton;

    private SlideshowStyleNameConstants styleNameConstants;
    private SlideshowButtonsPresenter presenter;

    @Inject
    public SlideshowButtonsViewImpl(SlideshowStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
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
        playPauseButton.setStateDown(down);
    }

    @Override
    public boolean isPlayButtonDown() {
        return playPauseButton.isStateDown();
    }

    @Override
    public Widget asWidget() {
        return buttonsPanel.asWidget();
    }

    @Override
    public void setPresenter(SlideshowButtonsPresenter presenter) {
        this.presenter = presenter;
    }

    private void addEventsHandlers() {
        playPauseButton.addClickHandler(createOnPlayClickCommand());
        stopButton.addClickHandler(createOnStopClickCommand());
        previousButton.addClickHandler(createOnPreviousClickCommand());
        nextButton.addClickHandler(createOnNextClickCommand());
    }

    private ClickHandler createOnStopClickCommand() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.onStopClick();
            }
        };
    }

    private ClickHandler createOnNextClickCommand() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.onNextClick();
            }
        };
    }

    private ClickHandler createOnPreviousClickCommand() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.onPreviousClick();
            }
        };
    }

    private ClickHandler createOnPlayClickCommand() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                presenter.onPlayPauseClick();
            }
        };
    }
}
