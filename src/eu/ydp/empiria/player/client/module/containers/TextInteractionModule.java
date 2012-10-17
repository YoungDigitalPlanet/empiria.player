package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Command;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IContainsText;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.module.span.SpanModule;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class TextInteractionModule extends BindingContainerModule<TextInteractionModule> implements IBookmarkable {

	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private final BookmarkingHelper bookmarkingHelper;

	public TextInteractionModule(){
		super();
		panel.setStyleName(styleNames.QP_TEXTINTERACTION());
		bookmarkingHelper = new BookmarkingHelper(panel);
	}
	
	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		super.initModule(element, moduleSocket, mil, bodyGeneratorSocket);
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
		return getView().getElement().getInnerText();
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
