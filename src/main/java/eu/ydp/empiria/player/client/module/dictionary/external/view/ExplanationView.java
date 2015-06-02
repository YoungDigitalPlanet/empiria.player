package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

public interface ExplanationView extends IsWidget {

    void processEntry(Entry entry);

    void show();

    void hide();

    void setExplanationStopButtonStyle();

    void setExplanationPlayButtonStyle();

    void setEntryPlayButtonStyle();

    void setEntryStopButtonStyle();

    void addEntryExamplePanelHandler(MouseUpHandler mouseUpHandler);

    void addPlayButtonHandler(ClickHandler clickHandler);

    void addEntryPlayButtonHandler(ClickHandler handler);

}
