package eu.ydp.empiria.player.client.module.bookmark;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

public class BookmarkingHelper {

	private Widget view;
	private String bookmarkingStyleName;

	public BookmarkingHelper(Widget view){
		this.view = view;
	}

	public void setBookmarkingStyleName(String styleName) {
		removeBookmarkingStyleName();
		this.bookmarkingStyleName = styleName;
		if (bookmarkingStyleName != null  &&  !"".equals(bookmarkingStyleName)){
			view.addStyleName(bookmarkingStyleName);
		}
	}

	public void removeBookmarkingStyleName() {
		if (bookmarkingStyleName != null  &&  !"".equals(bookmarkingStyleName)){
			view.removeStyleName(bookmarkingStyleName);
		}
	}

	public void setClickCommand(final Command command) {
		view.addDomHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				command.execute();
			}
		}, ClickEvent.getType());
	}
}
