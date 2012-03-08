package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.object.impl.AudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.VideoImpl;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class ObjectModule implements ISimpleModule {

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
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
			
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
		
		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-object-title");
		
		contentPanel = new FlowPanel();
		contentPanel.setStyleName("qp-object-content");
		if (widget != null)
			contentPanel.add(widget);
		
		descriptionPanel = new FlowPanel();
		descriptionPanel.setStyleName("qp-object-description");
		
		containerPanel.add(titlePanel);
		containerPanel.add(contentPanel);
		containerPanel.add(descriptionPanel);

		NodeList titleNodes = element.getElementsByTagName("title");
		if (titleNodes.getLength() > 0){
			Widget titleWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(titleNodes.item(0));
			if (titleWidget != null){
				titlePanel.add(titleWidget);
			}
		}
		NodeList descriptionNodes = element.getElementsByTagName("description");
		if (descriptionNodes.getLength() > 0){
			Widget descriptionWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(descriptionNodes.item(0));
			if (descriptionWidget != null){
				descriptionPanel.add(descriptionWidget);
			}
		}
		
	}

}
