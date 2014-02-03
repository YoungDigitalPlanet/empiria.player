package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.events.widgets.WidgetWorkflowListener;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class AssessmentBodyView extends FlowPanel {

	protected WidgetWorkflowListener widgetWorkflowListener;
	protected StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();

	public AssessmentBodyView(WidgetWorkflowListener wwl) {
		widgetWorkflowListener = wwl;

		setStyleName(styleNames.QP_ASSESSMENT_VIEW());
		this.sinkEvents(Event.ONCHANGE);
		this.sinkEvents(Event.ONMOUSEDOWN);
		this.sinkEvents(Event.ONMOUSEUP);
		this.sinkEvents(Event.ONMOUSEMOVE);
		this.sinkEvents(Event.ONMOUSEOUT);
	}

	public void init(Widget assessmentBodyWidget) {
		add(assessmentBodyWidget);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		widgetWorkflowListener.onLoad();
	}

	@Override
	public void onUnload() {
		super.onUnload();
		widgetWorkflowListener.onUnload();
	}

}
