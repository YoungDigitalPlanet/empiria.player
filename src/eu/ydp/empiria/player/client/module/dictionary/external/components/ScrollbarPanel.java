package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ScrollbarPanel extends Composite {

	private static ScrollbarPanelUiBinder uiBinder = GWT.create(ScrollbarPanelUiBinder.class);

	interface ScrollbarPanelUiBinder extends UiBinder<Widget, ScrollbarPanel> {
	}

	@Inject
	private StyleNameConstants styleNameConstants;

	@UiField
	AbsolutePanel scrollPanelContainer;
	@UiField
	FlowPanel scrollPanel;
	@UiField
	FlowPanel scrollHeader;
	@UiField
	FlowPanel scrollBody;
	@UiField
	FlowPanel scrollFooter;

	protected Timer scrollbarTimer;

	public ScrollbarPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		scrollbarTimer = new Timer() {

			@Override
			public void run() {
				hideScrollBar();
			}
		};
	}

	public Panel getScrollPanel() {
		return scrollPanelContainer;
	}

	public void updateScrollBar(Panel container, Panel contents) {
		int scrollTop = getScrollTop(contents.getElement());
		int heightAll = getHeight(contents.getElement());
		int heightVisible = container.getOffsetHeight();
		Integer scrollbarHeight = heightVisible * heightVisible / heightAll;
		int scrollbarTop;
		if (heightAll - heightVisible > 0) {
			scrollbarTop = (heightVisible - scrollbarHeight) * (scrollTop) / (heightAll - heightVisible);
		} else {
			scrollbarTop = 0;
		}
		scrollPanel.setHeight(scrollbarHeight.toString() + "px");
		scrollPanelContainer.setWidgetPosition(scrollPanel, 0, scrollbarTop);
		showScrollBar();
	}

	protected void hideScrollBar() {
		fadeOutJs(scrollPanel.getElement(), 200);
	}

	protected void showScrollBar() {
		scrollbarTimer.cancel();
		scrollbarTimer.schedule(500);
		opacityto(scrollPanel.getElement(), 100);
		scrollPanel.setStyleName(styleNameConstants.QP_DICTIONARY_SCROLL_CONTAINER());
	}

	public native int getScrollTop(Element el)/*-{
												return el.scrollTop;
												}-*/;

	public native void setScrollTop(Element el, int value)/*-{
															el.scrollTop = value;
															}-*/;

	protected native int getHeight(Element el)/*-{
												return el.scrollHeight;
												}-*/;

	protected native void opacityto(com.google.gwt.dom.client.Element elm, int v)/*-{
																					elm.style.opacity = v/100; 
																					elm.style.MozOpacity =  v/100; 
																					elm.style.KhtmlOpacity =  v/100; 
																					elm.style.filter=" alpha(opacity ="+v+")";
																					}-*/;

	protected native void fadeOutJs(com.google.gwt.dom.client.Element element, int fadeEffectTime)/*-{
																									var instance = this;
																									var _this = element;
																									instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.ScrollbarPanel::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, 100);
																									var delay = fadeEffectTime;
																									_this.style.zoom = 1; // for ie, set haslayout
																									_this.style.display="block"; 
																									for (i = 0; i <= 100; i+=5) {
																									(function(j) {
																									setTimeout(function() {
																									j=100-j;
																									instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.ScrollbarPanel::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, j);
																									},j*delay/100);
																									
																									})(i);     
																									}
																									}-*/;

}
