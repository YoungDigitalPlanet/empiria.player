package eu.ydp.empiria.player.client.module.object;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NamedNodeMap;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.button.FullScreenMediaButton;
import eu.ydp.empiria.player.client.module.media.button.MediaController;
import eu.ydp.empiria.player.client.module.media.button.MediaProgressBar;
import eu.ydp.empiria.player.client.module.media.button.MuteMediaButton;
import eu.ydp.empiria.player.client.module.media.button.PlayPauseMediaButton;
import eu.ydp.empiria.player.client.module.media.button.StopMediaButton;
import eu.ydp.empiria.player.client.module.media.button.VolumeMediaButton;
import eu.ydp.empiria.player.client.module.media.info.MediaCurrentTime;
import eu.ydp.empiria.player.client.module.media.info.MediaTotalTime;
import eu.ydp.empiria.player.client.module.media.info.PositionInMediaStream;

public class TemplateParser<T extends Widget> {
	protected Map<String, MediaController<?>> controllers = new HashMap<String, MediaController<?>>();
	protected MediaWrapper<T> mediaDescriptor = null;
	private final static String MODULE_ATTRIBUTE = "module";

	public TemplateParser(MediaWrapper<T> mediaDescriptor) {
		this.mediaDescriptor = mediaDescriptor;
		controllers.put(ModuleTagName.MEDIA_PLAY_PAUSE_BUTTON.tagName(), new PlayPauseMediaButton());
		controllers.put(ModuleTagName.MEDIA_STOP_BUTTON.tagName(), new StopMediaButton());
		controllers.put(ModuleTagName.MEDIA_MUTE_BUTTON.tagName(), new MuteMediaButton());
		controllers.put(ModuleTagName.MEDIA_PROGRESS_BAR.tagName(), (MediaController<?>) GWT.create(MediaProgressBar.class));
		controllers.put(ModuleTagName.MEDIA_FULL_SCREEN_BUTTON.tagName(), new FullScreenMediaButton());
		controllers.put(ModuleTagName.MEDIA_POSITION_IN_STREAM.tagName(), new PositionInMediaStream());
		controllers.put(ModuleTagName.MEDIA_VOLUME_BAR.tagName(), new VolumeMediaButton());
		controllers.put(ModuleTagName.MEDIA_CURRENT_TIME.tagName(), new MediaCurrentTime());
		controllers.put(ModuleTagName.MEDIA_TOTAL_TIME.tagName(), new MediaTotalTime());
	}

	private void parseXMLAttributes(com.google.gwt.xml.client.Element srcElement, com.google.gwt.dom.client.Element dstElement) {
		NamedNodeMap attributes = srcElement.getAttributes();
		for (int i = 0; i < attributes.getLength(); i++) {
			Node attribute = attributes.item(i);
			if (attribute.getNodeValue().length() > 0) {
				if (attribute.getNodeName().equals("class")) {
					dstElement.addClassName(attribute.getNodeValue());
				} else if (!MODULE_ATTRIBUTE.equals(attribute.getNodeName())) {
					dstElement.setAttribute(attribute.getNodeName(), attribute.getNodeValue());
				}
			}
		}
	}

	public void parse(Node mainNode, Widget parent) {
		if (mainNode == null) {
			return;
		}
		NodeList nodes = mainNode.getChildNodes();
		for (int x = 0; x < nodes.getLength(); ++x) {
			Node node = nodes.item(x);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String moduleName = node.getNodeName();
				if (moduleName.trim().length() > 0 && controllers.containsKey(moduleName)) {
					MediaController<?> mc = (MediaController<?>) controllers.get(moduleName).getNewInstance();
					mc.setMediaDescriptor(mediaDescriptor);
					mc.init();
					parseXMLAttributes((Element) node, mc.getElement());
					if (parent instanceof ComplexPanel) {
						((Panel) parent).add(mc);
						parse(node, mc);
					}
				} else {
					HTMLPanel panel = new HTMLPanel(((Element) node).getNodeName(), "");
					if (parent instanceof ComplexPanel) {
						((Panel) parent).add(panel);
						parseXMLAttributes((Element) node, panel.getElement());
						parse(node, panel);
					}
				}
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				parent.getElement().appendChild(Document.get().createTextNode(node.getNodeValue()));
			}
		}

	}
}
