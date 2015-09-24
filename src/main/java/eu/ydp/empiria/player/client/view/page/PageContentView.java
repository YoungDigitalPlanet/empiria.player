package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class PageContentView extends Composite {

    private static PageContentUiBinder uiBinder = GWT.create(PageContentUiBinder.class);

    @UiTemplate("PageContentView.ui.xml")
    interface PageContentUiBinder extends UiBinder<Widget, PageContentView> {
    }

    @UiField
    Panel itemsPanel;
    @UiField
    Panel titlePanel;

    @Inject
    public PageContentView(@Assisted Panel parentPanel) {
        uiBinder.createAndBindUi(this);
        parentPanel.add(itemsPanel);
    }

    public Panel getItemsPanel() {
        return itemsPanel;
    }

    public Panel getTitlePanel() {
        return titlePanel;
    }
}
