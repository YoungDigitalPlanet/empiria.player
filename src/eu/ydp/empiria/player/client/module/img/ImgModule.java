package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class ImgModule implements ISimpleModule {

	protected Image img;
	
	protected Panel containerPanel;
	protected Panel titlePanel;
	protected Panel descriptionPanel;
	protected Panel contentPanel;
	protected boolean extended = false;
	
	public ImgModule(){
		img = new Image();
		img.setStyleName("qp-img");
	}
	
	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		String src = element.getAttribute("src");
		if (src != null)
			img.setUrl(src);
		String alt = element.getAttribute("alt");
		if (alt != null)
			img.setAltText(alt);
		String cls = element.getAttribute("class");
		if (cls != null)
			img.addStyleName(cls);
		String id = element.getAttribute("id");
		if (id != null  &&  !"".equals(id)  &&  getView() != null){
			getView().getElement().setId(id);
		}
		String extendedAttr = element.getAttribute("extended");
		if (extendedAttr != null  &&  "true".equals(extendedAttr.toLowerCase()))
			extended = true;
		
		if (extended){
			
			containerPanel = new FlowPanel();
			containerPanel.setStyleName("qp-img-container");
			
			titlePanel = new FlowPanel();
			titlePanel.setStyleName("qp-img-title");
			
			contentPanel = new FlowPanel();
			contentPanel.setStyleName("qp-img-content");
			contentPanel.add(img);
			
			descriptionPanel = new FlowPanel();
			descriptionPanel.setStyleName("qp-img-description");
			
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
	
	@Override
	public Widget getView() {
		if (extended)
			return containerPanel;
		return img;
		
	}

}
