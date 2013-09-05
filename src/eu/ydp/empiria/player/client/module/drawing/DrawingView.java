package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingView implements IsWidget {

	@Inject @ModuleScoped DrawingBean bean;

	@Override
	public Widget asWidget() {
		return new FlowPanel();
	}

	public void bindView(){

	}

}
