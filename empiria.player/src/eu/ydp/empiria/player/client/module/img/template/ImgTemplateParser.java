package eu.ydp.empiria.player.client.module.img.template;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_IMG_MODE;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.img.DefaultImgContent;
import eu.ydp.empiria.player.client.module.img.ExplorableImgContent;
import eu.ydp.empiria.player.client.module.img.ImgContent;
import eu.ydp.empiria.player.client.module.img.LabelledImgContent;
import eu.ydp.empiria.player.client.module.media.button.FullScreenMediaButton;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.util.AbstractTemplateParser;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ImgTemplateParser extends AbstractTemplateParser {
	protected Set<String> controllers = new HashSet<String>();
	private final Element baseElement;
	private final ModuleSocket moduleSocket;

	public ImgTemplateParser(Element baseElement, ModuleSocket moduleSocket) {
		this.baseElement = baseElement;
		this.moduleSocket = moduleSocket;
		controllers.add(ModuleTagName.MEDIA_TITLE.tagName());
		controllers.add(ModuleTagName.MEDIA_DESCRIPTION.tagName());
		controllers.add(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName());
		controllers.add(ModuleTagName.MEDIA_SCREEN.tagName());
	}

	@Override
	protected MediaController<?> getMediaControllerNewInstance(String moduleName, Node node) {
		MediaController<?> controller = null;
		if (isModuleSupported(moduleName)) {

			ModuleTagName tagName = ModuleTagName.getTag(moduleName);
			switch (tagName) {
			case MEDIA_TITLE:
				controller = createWrapper("title");
				break;
			case MEDIA_SCREEN:
				controller = createScreen();
				break;
			case MEDIA_DESCRIPTION:
				controller = createWrapper("description");
				break;
			case MEDIA_FULL_SCREEN_BUTTON:
				controller = new FullScreenMediaButton();
				break;
			default:
				break;
			}
		}
		return controller;
	}

	/**
	 * tworzy widget na podstawie wezlow xml poprzez {@link InlineBodyGenerator}
	 * @param elementName
	 * @return
	 */
	private ModuleWrapper createWrapper(String elementName){
		ModuleWrapper moduleWrapper = null;
		NodeList titleNodes = baseElement.getElementsByTagName(elementName);
		if (titleNodes.getLength() > 0) {
			 Widget widget  = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(XMLUtils.getFirstChildElement((Element) titleNodes.item(0)));
			 if(widget!=null){
				 moduleWrapper = new ModuleWrapper(widget);
			 }
		}
		return moduleWrapper;
	}

	/**
	 * Tworzy obiekt  img + labele
	 * @return
	 */
	private MediaController<?> createScreen() {
		ImgContent content;
		if (baseElement.getElementsByTagName("label").getLength() > 0) {
			content = new LabelledImgContent();
		} else {
			Map<String, String> styles = moduleSocket.getStyles(baseElement);
			if (styles.containsKey(EMPIRIA_IMG_MODE) && styles.get(EMPIRIA_IMG_MODE).equalsIgnoreCase("explorable")) {
				content = new ExplorableImgContent();
			} else {
				content = new DefaultImgContent();
			}
		}
		content.init(baseElement, moduleSocket);
		return new ModuleWrapper((Widget) content);
	}

	@Override
	protected boolean isModuleSupported(String moduleName) {
		return controllers.contains(moduleName);
	}

}
