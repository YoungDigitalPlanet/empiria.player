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
    private static AccordionSectionUiBinder uiBinder = GWT.create(AccordionSectionUiBinder.class);
    private UserInteractionHandlerFactory userInteractionHandlerFactory;


    @UiTemplate("AccordionSectionView.ui.xml")
    interface AccordionSectionUiBinder extends UiBinder<Widget, AccordionSectionViewImpl> {
    }

    @UiField
    SimplePanel title;
    @UiField
    FlowPanel content;
    @UiField
    SimplePanel contentWrapper;
    @UiField
    FlowPanel section;

    @Inject
    public AccordionSectionViewImpl(UserInteractionHandlerFactory userInteractionHandlerFactory) {
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
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
    public void addClickCommand(Command clickCommand) {
        userInteractionHandlerFactory.applyUserClickHandler(clickCommand, title);
    }

    @Override
    public void addSectionStyleName(String style) {
        section.addStyleName(style);
    }

    @Override
    public void addContentWrapperStyleName(String style) {
        contentWrapper.addStyleName(style);
    }

    @Override
    public void removeSectionStyleName(String style) {
        section.removeStyleName(style);
    }

    @Override
    public void removeContentWrapperStyleName(String style) {
        contentWrapper.removeStyleName(style);
    }

    @Override
    public int getContentHeight() {
        return content.getOffsetHeight();
    }

    @Override
    public int getContentWidth() {
        return content.getOffsetWidth();
    }

    @Override
    public void setSectionDimensions(String width, String height) {
        contentWrapper.setWidth(width);
        contentWrapper.setHeight(height);
    }
}
