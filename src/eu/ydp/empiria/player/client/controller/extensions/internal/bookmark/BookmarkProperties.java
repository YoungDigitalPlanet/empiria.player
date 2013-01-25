package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import com.google.gwt.core.client.JavaScriptObject;

public class BookmarkProperties extends JavaScriptObject implements IBookmarkProperties{

	protected BookmarkProperties(){}
	
	protected static BookmarkProperties newInstance() {
		BookmarkProperties bp = JavaScriptObject.createObject().cast();
		bp.setBookmarkIndex(-1);
		bp.setBookmarkTitle("");
		
		
		bp.touch();
		return bp;
	}
	
	public final native int getBookmarkIndex() /*-{
		return bookmarkIndex;
	}-*/;

	public final native void setBookmarkIndex(int bookmarkIndex) /*-{
		this.bookmarkIndex = bookmarkIndex;
	}-*/;

	public final native String getBookmarkTitle() /*-{
		return bookmarkTitle;
	}-*/;

	public final native void setBookmarkTitle(String bookmarkTitle) /*-{
		this.bookmarkTitle = bookmarkTitle;
	}-*/;
	
	public final native long getTimestamp() /*-{
		return this.timestamp;
	}-*/;

	public final native void touch() /*-{
		this.timestamp = new Date().getTime();
	}-*/;
	
}
