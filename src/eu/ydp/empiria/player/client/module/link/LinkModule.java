package eu.ydp.empiria.player.client.module.link;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.SimpleContainerModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.NumberUtils;

public class LinkModule extends SimpleContainerModuleBase<LinkModule> {

	protected FlowRequestInvoker flowRequestInvoker;

	protected Panel mainPanel;

	protected int itemIndex = -1;
	protected String url;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	public LinkModule(FlowRequestInvoker flowRequestInvoker) {
		super();
		this.flowRequestInvoker = flowRequestInvoker;
		panel.setStyleName(styleNames.QP_LINK_CONTENT());
		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_LINK());
		mainPanel.add(panel);

		mainPanel.addDomHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				processLink();
			}
		}, ClickEvent.getType());

		mainPanel.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				mainPanel.setStyleName(styleNames.QP_LINK_OVER());
			}
		}, MouseOverEvent.getType());
		mainPanel.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				mainPanel.setStyleName(styleNames.QP_LINK());
			}
		}, MouseOutEvent.getType());
	}


	@Override
	public void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener mil, BodyGeneratorSocket bodyGeneratorSocket) {
		super.initModule(element, moduleSocket, mil, bodyGeneratorSocket);

		if (element.hasAttribute("itemIndex")){
			itemIndex = NumberUtils.tryParseInt( element.getAttribute("itemIndex"), -1);
		}
		if (itemIndex == -1  &&   element.hasAttribute("url")){
			url = element.getAttribute("url");
		}

	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	@Override
	public HasWidgets getContainer() {
		return panel;
	}

	protected void processLink(){
		if (itemIndex != -1){
			flowRequestInvoker.invokeRequest(new FlowRequest.NavigateGotoItem(itemIndex) );
		} else if (url != null){
			Window.open(url, "_blank", "");
		}
	}

	@Override
	public LinkModule getNewInstance() {
		return null;
	}

}
