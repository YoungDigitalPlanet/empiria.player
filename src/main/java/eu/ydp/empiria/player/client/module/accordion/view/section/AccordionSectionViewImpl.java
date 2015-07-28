package eu.ydp.empiria.player.client.module.accordion.view.section;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class AccordionSectionViewImpl extends Composite implements AccordionSectionView {
    private static final String PX = Style.Unit.PX.toString();
    private static AccordionSectionUiBinder uiBinder = GWT.create(AccordionSectionUiBinder.class);
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    private StyleNameConstants styleNameConstants;


    @UiTemplate("AccordionSectionView.ui.xml")
    interface AccordionSectionUiBinder extends UiBinder<Widget, AccordionSectionViewImpl> {
    }

    @UiField
    SimplePanel title;
    @UiField
    SimplePanel content;
    @UiField
    FlowPanel wrapper;

    @Inject
    public AccordionSectionViewImpl(UserInteractionHandlerFactory userInteractionHandlerFactory, StyleNameConstants styleNameConstants) {
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
        this.styleNameConstants = styleNameConstants;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTitle(Widget widget) {
        title.setWidget(widget);
    }

    @Override
    public HasWidgets getContentContainer() {
        return content;
    }

    @Override
    public void addClickEvent(Command clickCommand) {
        userInteractionHandlerFactory.applyUserClickHandler(clickCommand, title);
    }

    @Override
    public void hideVertically() {
        wrapper.addStyleName(styleNameConstants.QP_ACCORDION_HIDDEN());
        content.addStyleName(styleNameConstants.QP_ZERO_HEIGHT());
    }

    @Override
    public void hideHorizontally() {
        wrapper.addStyleName(styleNameConstants.QP_ACCORDION_HIDDEN());
        content.addStyleName(styleNameConstants.QP_ZERO_WIDTH());
    }

    @Override
    public void showVertically() {
        updateSize();
        wrapper.removeStyleName(styleNameConstants.QP_ACCORDION_HIDDEN());
        content.removeStyleName(styleNameConstants.QP_ZERO_HEIGHT());
    }

    @Override
    public void showHorizontally() {
        updateSize();
        wrapper.removeStyleName(styleNameConstants.QP_ACCORDION_HIDDEN());
        content.removeStyleName(styleNameConstants.QP_ZERO_WIDTH());
    }

    @Override
    public void init(Transition transition) {
        switch (transition) {
            case ALL:
                content.addStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_ALL());
                break;
            case WIDTH:
                content.addStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_WIDTH());
                break;
            case HEIGHT:
                content.addStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_HEIGHT());
                break;
        }
    }

    private void updateSize() {
        Widget bodyContent = title.getWidget();
        int h = bodyContent.getElement().getOffsetHeight();
        int w = bodyContent.getElement().getOffsetWidth();

        content.setHeight(h + PX);
        content.setWidth(w + PX);
    }
}
