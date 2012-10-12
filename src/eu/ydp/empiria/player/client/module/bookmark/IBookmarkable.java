package eu.ydp.empiria.player.client.module.bookmark;

import com.google.gwt.user.client.Command;

import eu.ydp.empiria.player.client.module.IModule;

public interface IBookmarkable extends IModule {

	void setBookmarkingStyleName(String styleName);
	
	void removeBookmarkingStyleName();
	
	void setClickCommand(Command command);
	
	String getBookmarkHtmlBody();
}