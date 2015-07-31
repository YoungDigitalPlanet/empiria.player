package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;

public class AccordionSectionPresenter implements IsWidget {
    private static final String PX = Style.Unit.PX.toString();

    private final StyleNameConstants styleNameConstants;
    private final AccordionSectionView view;

    @Inject
    public AccordionSectionPresenter(AccordionSectionView view, StyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        this.view = view;
    }

    public void setTitle(Widget widget) {
        view.setTitle(widget);
    }

    public HasWidgets getContentContainer() {
        return view.getContentContainer();
    }

    public void addClickCommand(Command clickCommand) {
        view.addClickCommand(clickCommand);
    }

    public void hideVertically() {
        view.addSectionStyleName(styleNameConstants.QP_ACCORDION_SECTION_HIDDEN());
        view.addContentWrapperStyleName(styleNameConstants.QP_ZERO_HEIGHT());
    }

    public void hideHorizontally() {
        view.addSectionStyleName(styleNameConstants.QP_ACCORDION_SECTION_HIDDEN());
        view.addContentWrapperStyleName(styleNameConstants.QP_ZERO_WIDTH());
    }

    public void show() {
        updateSize();
        view.removeSectionStyleName(styleNameConstants.QP_ACCORDION_SECTION_HIDDEN());
        view.removeContentWrapperStyleName(styleNameConstants.QP_ZERO_HEIGHT());
        view.removeContentWrapperStyleName(styleNameConstants.QP_ZERO_WIDTH());
    }

    public void init(Transition transition) {
        switch (transition) {
            case ALL:
                view.addContentWrapperStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_ALL());
                break;
            case WIDTH:
                view.addContentWrapperStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_WIDTH());
                break;
            case HEIGHT:
                view.addContentWrapperStyleName(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_HEIGHT());
                break;
        }
    }

    private void updateSize() {
        int h = view.getContentHeight();
        int w = view.getContentWidth();
        view.setSectionDimensions(w + PX, h + PX);
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
}
