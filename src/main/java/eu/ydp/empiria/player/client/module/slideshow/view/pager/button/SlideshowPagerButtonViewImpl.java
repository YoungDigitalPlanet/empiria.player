package eu.ydp.empiria.player.client.module.slideshow.view.pager.button;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.TwoStateButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SlideshowPagerButtonViewImpl extends Composite implements SlideshowPagerButtonView {

    @UiTemplate("SlideshowPagerButtonView.ui.xml")
    interface SlideshowPagerButtonUiBinder extends UiBinder<Widget, SlideshowPagerButtonViewImpl> {
    }

    ;

    private final SlideshowPagerButtonUiBinder uiBinder = GWT.create(SlideshowPagerButtonUiBinder.class);

    @UiField
    protected TwoStateButton pagerButton;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Inject
    private StyleNameConstants styleNameConstants;

    public SlideshowPagerButtonViewImpl() {
        uiBinder.createAndBindUi(this);
        initWidget(pagerButton);
    }

    @Override
    public void setOnClickCommand(Command onClickCommand) {
        userInteractionHandlerFactory.applyUserClickHandler(onClickCommand, pagerButton);
    }

    @Override
    public void activatePagerButton() {
        pagerButton.setStateDown(true);
    }

    @Override
    public void deactivatePagerButton() {
        pagerButton.setStateDown(false);
    }

    @UiFactory
    protected TwoStateButton createTwoStateButton() {
        return new TwoStateButton(styleNameConstants.QP_SLIDESHOW_PAGER_BUTTON(), styleNameConstants.QP_SLIDESHOW_PAGER_BUTTON_ACTIVE());
    }
}
