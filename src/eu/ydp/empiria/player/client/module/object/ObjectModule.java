package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.object.impl.AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.VideoImpl;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class ObjectModule extends SimpleModuleBase implements Factory<ObjectModule> {

	protected Widget widget;

	protected Panel containerPanel;
	protected Panel titlePanel;
	protected Panel descriptionPanel;
	protected Panel contentPanel;

	@Override
	public Widget getView() {
		return containerPanel;
	}

	@Override
	public void initModule(Element element) {

		String html;
		String src = XMLUtils.getAttributeAsString(element, "data");
		String type = XMLUtils.getAttributeAsString(element, "type");

		if(type.startsWith("video")){
			VideoImpl video = GWT.create(VideoImpl.class);
			video.setSrc(src);
			widget = video.asWidget();
		}
		else{
			AudioImpl audio = GWT.create(AudioImpl.class);
			html = audio.getHTML(src);
			widget = new HTML(html);
		}

		containerPanel = new FlowPanel();
		containerPanel.setStyleName("qp-object-container");
		String cls = element.getAttribute("class");
		if (cls != null  &&  !"".equals(cls))
			containerPanel.addStyleName(cls);

		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-object-title");

		contentPanel = new FlowPanel();
		contentPanel.setStyleName("qp-object-content");
		if (widget != null)
			contentPanel.add(widget);

		descriptionPanel = new FlowPanel();
		descriptionPanel.setStyleName("qp-object-description");

		containerPanel.add(contentPanel);
		containerPanel.add(titlePanel);
		containerPanel.add(descriptionPanel);

		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0){
			Widget titleWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null){
				titlePanel.add(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0){
			Widget descriptionWidget = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null){
				descriptionPanel.add(descriptionWidget);
			}
		}

	}
	@Override
	public ObjectModule getNewInstance() {
		return new ObjectModule();
	}

}
