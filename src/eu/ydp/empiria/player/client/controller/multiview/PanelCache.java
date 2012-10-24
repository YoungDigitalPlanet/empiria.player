package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.view.player.AbstractElementCache;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

public class PanelCache extends AbstractElementCache<KeyValue<FlowPanel, FlowPanel>> {
	@Inject
	protected StyleNameConstants styleNames;
	@Inject
	protected GWTPanelFactory panelFactory;

	protected boolean swipeDisabled;

	private FlowPanel parent;

	private final static float WIDTH = 100;

	@Override
	protected KeyValue<FlowPanel, FlowPanel> getElement(Integer index) {
		parent = panelFactory.getFlowPanel();
		FlowPanel childPanel = panelFactory.getFlowPanel();

		Style style = parent.getElement().getStyle();
		parent.getElement().setId(styleNames.QP_PAGE() + index.intValue());

		if (!swipeDisabled) {
			style.setPosition(Position.ABSOLUTE);
			style.setTop(0, Unit.PX);
			style.setLeft(WIDTH * index, Unit.PCT);
			style.setWidth(WIDTH, Unit.PCT);
		}

		childPanel.setHeight("100%");
		childPanel.setWidth("100%");
		parent.add(childPanel);
		return new KeyValue<FlowPanel, FlowPanel>(parent, childPanel);
	}

	public void setSwipeDisabled(boolean swipeDisabled) {
		this.swipeDisabled = swipeDisabled;
		if (parent != null){
			Style style = parent.getElement().getStyle();

			if(swipeDisabled){
				style.clearTop();
				style.clearPosition();
			}else{
				style.setTop(0, Unit.PX);
				style.setPosition(Position.ABSOLUTE);
			}
		}
	}

}
