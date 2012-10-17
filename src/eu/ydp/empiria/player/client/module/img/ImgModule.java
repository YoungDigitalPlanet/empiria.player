package eu.ydp.empiria.player.client.module.img;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_MODE;

import java.util.Map;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.InlineModuleBase;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.module.img.template.ImgTemplateParser;
import eu.ydp.gwtutil.client.geom.Rectangle;
import eu.ydp.gwtutil.client.xml.XMLUtils;

/**
 * Klasa odpowiedzialna za renderwoanie elementu img.
 *
 */
public class ImgModule extends InlineModuleBase implements Factory<ImgModule>, IBookmarkable {

	protected ImgModuleView view;
	private String imageSource;
	private BookmarkingHelper bookmarkingHelper;

	public ImgModule() {
		view = new ImgModuleView();
		bookmarkingHelper = new BookmarkingHelper(view);
	}

	/**
	 * buduke widok na starych zasadach
	 * @param element
	 */
	protected void createOldView(Element element) {
		ImgContent content;
		imageSource = element.getAttribute("src");
		if (element.getElementsByTagName("label").getLength() > 0) {
			content = new LabelledImgContent();
		} else {
			Map<String, String> styles = getModuleSocket().getStyles(element);
			if (styles.containsKey(EMPIRIA_IMG_MODE) && styles.get(EMPIRIA_IMG_MODE).equalsIgnoreCase("explorable")) {
				content = new ExplorableImgContent();
			} else {
				content = new DefaultImgContent();
			}
		}
		content.init(element, getModuleSocket());
		view.setContent(content);
		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0) {
			Widget titleWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null) {
				view.setTitle(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0) {
			Widget descriptionWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null) {
				view.setDescription(descriptionWidget);
			}
		}
	}

	/**
	 * buduje widok w oparciu o template
	 * @param baseElement
	 * @param template
	 */
	protected void createViewFromTemplate(Element baseElement, Element template) {
		ImgTemplateParser templateParser = new ImgTemplateParser(baseElement, getModuleSocket());
		view.getContainerPanel().clear();
		templateParser.parse(template, view.getContainerPanel());
	}

	@Override
	protected void initModule(Element element) {
		final Element templateElement = XMLUtils.getFirstElementWithTagName(element, "template");
		if (templateElement == null) {
			createOldView(element);
		} else {
			createViewFromTemplate(element, templateElement);
		}
	}

	@Override
	public Widget getView() {
		return view;
	}

	@Override
	public ImgModule getNewInstance() {
		return new ImgModule();
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
		return "<img src=\"" + imageSource + "\"/>";
	}

	@Override
	public Rectangle getViewArea() {
		return bookmarkingHelper.getViewArea();
	}

	@Override
	public String getDefaultBookmarkTitle() {
		return "";
	}

}
