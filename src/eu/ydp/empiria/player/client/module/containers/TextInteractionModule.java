package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.Command;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;


public class TextInteractionModule extends BindingContainerModule<TextInteractionModule> implements IBookmarkable {

	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private BookmarkingHelper bookmarkingHelper;
	
	public TextInteractionModule(){
		super();
		panel.setStyleName(styleNames.QP_TEXTINTERACTION());
		bookmarkingHelper = new BookmarkingHelper(panel);
	}

	@Override
	public TextInteractionModule getNewInstance() {
		return new TextInteractionModule();
	}

	@Override
	public void setBookmarkingStyleName(String styleName) {
		bookmarkingHelper.setBookmarkingStyleName(styleName);
	}

	@Override
	public void removeBookmarkingStyleName() {
		bookmarkingHelper.removeBookmarkingStyleName();
	}

	@Override
	public void setClickCommand(final Command command) {
		bookmarkingHelper.setClickCommand(command);
	}

	@Override
	public String getBookmarkHtmlBody() {
		return "text interaction fake body";
	}
	

}
