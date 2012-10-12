package eu.ydp.empiria.player.client.view.page;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.view.item.ItemContentView;
import eu.ydp.empiria.player.client.view.item.ItemViewSocket;

public class PageViewSocketImpl implements PageViewSocket {
	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants(); //NOPMD
	private final PageContentView view;
	private Panel contentPanel;
	private ItemContentView[] items;

	public PageViewSocketImpl(PageContentView view) {
		this.view = view;
	}
	@Override
	public ItemViewSocket getItemViewSocket(int index) {
		return items[index];
	}

	@SuppressWarnings("PMD")
	@Override
	public void initItemViewSockets(int count) {
		view.getItemsPanel().clear();
		Panel[] itemPanels = new Panel[count];
		items = new ItemContentView[count];
		for (int i = 0; i < count; i++) {
			itemPanels[i] = new FlowPanel();
			itemPanels[i].setStyleName(styleNames.QP_PAGE_ITEM());
			items[i] = new ItemContentView(itemPanels[i]);
			view.getItemsPanel().add(itemPanels[i]);
		}

	}

	@Override
	public void setPageViewCarrier(PageViewCarrier pageViewCarrier) {
		view.getTitlePanel().clear();
		view.getTitlePanel().add(pageViewCarrier.getPageTitle());
		if (pageViewCarrier.hasContent()) {
			contentPanel = new FlowPanel();
			if (pageViewCarrier.pageType == PageType.ERROR) {
				contentPanel.setStyleName(styleNames.QP_PAGE_ERROR());
				Label errorLabel = new Label(pageViewCarrier.errorMessage);
				errorLabel.setStyleName(styleNames.QP_PAGE_ERROR_TEXT());
				contentPanel.add(errorLabel);

			} else if (pageViewCarrier.pageType == PageType.LESSON_SKIN) {
				view.setParentPanel(pageViewCarrier.getPageSlot());
			}

			if (pageViewCarrier.pageType != PageType.LESSON_SKIN) {
				view.getItemsPanel().clear();
				view.getItemsPanel().add(contentPanel);
			}
		}
	}
}
