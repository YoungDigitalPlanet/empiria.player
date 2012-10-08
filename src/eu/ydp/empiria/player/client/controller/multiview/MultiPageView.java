package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class MultiPageView extends FlowPanel implements ResizeHandler {
	private MultiPageController controller;

	public MultiPageView() {
		Window.addResizeHandler(this);
	}

	public void setController(MultiPageController controller) {
		this.controller = controller;
	}

	@Override
	protected void onAttach() {
		setSwipeDisabled(controller.isSwipeDisabled());
		super.onAttach();
	}
	
	public void setSwipeDisabled(boolean swipeDisabled){
		Style style = controller.getMainPanel().getElement().getStyle();
		Style elmentStyle = getElement().getStyle();
		
		style.setWidth(controller.getWidth(), Unit.PCT);
		
		if (swipeDisabled) {
			style.clearPosition();
			style.clearTop();
			style.clearLeft();
			elmentStyle.clearPosition();
		}else{
			style.setPosition(Position.ABSOLUTE);
			style.setTop(0, Unit.PX);
			style.setLeft(0, Unit.PX);
			elmentStyle.setPosition(Position.RELATIVE);
		}
		
		setSwipeLength();
	}

	@Override
	public void onResize(ResizeEvent event) {
		setSwipeLength();
	}

	private void setSwipeLength() {
		controller.setSwipeLength(RootPanel.get().getOffsetWidth() / 5);
	}
}
