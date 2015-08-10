package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.choice.ChoiceStyleNameConstants;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.choice.providers.SimpleChoiceStyleProvider;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

public class SimpleChoiceViewImpl implements SimpleChoiceView {
    @UiField
    Panel optionPanel;

    @UiField
    Panel mainPanel;

    @UiField
    Panel cover;

    @UiField
    Panel contentWidgetPlace;

    @UiField
    Panel markAnswersPanel;

    @UiField
    Panel labelPanel;

    @UiField
    Panel buttonPlace;

    private Widget widget;

    private SimpleChoicePresenter presenter;

    private SimpleChoiceStyleProvider styleProvider;

    private ChoiceButtonBase button;

    @Inject
    private ChoiceStyleNameConstants styleNameConstants;

    private static SimpleChoiceViewUiBinder uiBinder = GWT.create(SimpleChoiceViewUiBinder.class);

    @UiTemplate("SimpleChoiceView.ui.xml")
    interface SimpleChoiceViewUiBinder extends UiBinder<Widget, SimpleChoiceViewImpl> {
    }

    @Inject
    public SimpleChoiceViewImpl(@Assisted SimpleChoicePresenter presenter, @Assisted SimpleChoiceStyleProvider styleProvider) {
        widget = uiBinder.createAndBindUi(this);
        this.presenter = presenter;
        this.styleProvider = styleProvider;
        addListenersToCover();
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void markCorrect() {
        removeInactiveStyle();

        markAnswersPanel.setStyleName(styleProvider.getMarkCorrectStyle());
        markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
        optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
        labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
    }

    @Override
    public void markWrong() {
        removeInactiveStyle();
        markAnswersPanel.setStyleName(styleProvider.getMarkWrongStyle());
        markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
        optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
        labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
    }

    @Override
    public void unmarkCorrect() {
        addInactiveStyle();
        optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
        labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
    }

    @Override
    public void unmarkWrong() {
        addInactiveStyle();
        optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
        labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
    }

    private void addInactiveStyle() {
        markAnswersPanel.setStyleName(styleProvider.getInactiveStyle());
        markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
        optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
        labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
        labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
    }

    private void removeInactiveStyle() {
        optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
        labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
    }

    @Override
    public Widget getFeedbackPlaceHolder() {
        return labelPanel;
    }

    @Override
    public void reset() {
        button.setSelected(false);
        markAnswersPanel.setStyleName(styleProvider.getResetStyle());
    }

    @Override
    public void setLocked(boolean locked) {
        button.setButtonEnabled(!locked);
    }

    @Override
    public void setButton(ChoiceButtonBase button) {
        this.button = button;
        buttonPlace.add(button);
        addButtonListeners();
        setAnswerePanelStyle();
    }

    private void setAnswerePanelStyle() {
        markAnswersPanel.addStyleName(styleProvider.getAnswereStyle());
    }

    @Override
    public void setContent(Widget contentWidget) {
        contentWidgetPlace.add(contentWidget);
    }

    private void addButtonListeners() {
        button.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                setMouseOver();
            }
        });

        button.addMouseOutHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                setMouseOut();
            }
        });

        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onChoiceClick();
            }
        });
    }

    private void addListenersToCover() {
        cover.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                setMouseOver();
            }
        }, MouseOverEvent.getType());
        cover.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                setMouseOut();
            }
        }, MouseOutEvent.getType());
        cover.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onChoiceClick();
            }
        }, ClickEvent.getType());
    }

    private void setMouseOver() {
        button.setMouseOver(true);
    }

    private void setMouseOut() {
        button.setMouseOver(false);
    }

    private void onChoiceClick() {
        presenter.onChoiceClick();
    }

    @Override
    public void setSelected(boolean select) {
        button.setSelected(select);
    }

    @Override
    public boolean isSelected() {
        return button.isSelected();
    }

}
