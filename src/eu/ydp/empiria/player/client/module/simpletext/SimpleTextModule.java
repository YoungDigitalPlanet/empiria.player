package eu.ydp.empiria.player.client.module.simpletext;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class SimpleTextModule extends SimpleModuleBase implements Factory<SimpleTextModule>, IBookmarkable {

	protected Widget contents;
	private final StyleNameConstants styleNames = eu.ydp.empiria.player.client.gin.PlayerGinjector.INSTANCE.getStyleNameConstants();
	private final BookmarkingHelper bookmarkingHelper;

	public SimpleTextModule(){
		contents = new ElementWrapperWidget(Document.get().createPElement());
		contents.setStyleName(styleNames.QP_SIMPLETEXT());
		bookmarkingHelper = new BookmarkingHelper(contents);
	}

	@Override
	public void initModule(Element element) {
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element, contents.getElement());
	}

	@Override
	public Widget getView() {
		return contents;
	}

	@Override
	public SimpleTextModule getNewInstance() {
		return new SimpleTextModule();
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
		return contents.getElement().getInnerHTML().trim();
	}

	@Override
	public Rectangle getViewArea() {
		return bookmarkingHelper.getViewArea();
	}

	@Override
	public String getDefaultBookmarkTitle() {
		return bookmarkingHelper.getDefaultBookmarkTitle(getView().getElement().getInnerText());
	}
}