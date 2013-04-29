package eu.ydp.empiria.player.client.module.labelling.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.labelling.structure.ImgBean;

public class LabellingViewImpl extends Composite implements LabellingView {

	private static LabellingViewImplUiBinder uiBinder = GWT.create(LabellingViewImplUiBinder.class);

	interface LabellingViewImplUiBinder extends UiBinder<Widget, LabellingViewImpl> {}
	
	@UiField AbsolutePanel panel;
	@UiField Image image;

	public LabellingViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setBackground(ImgBean imgBean) {
		image.setUrl(imgBean.getSrc());
		setSize(imgBean.getWidth(), imgBean.getHeight());
	}

	private void setSize(int width, int height) {
		String px = Unit.PX.toString().toLowerCase();
		setWidth(width + px);
		setHeight(height + px);
		panel.setWidth(width + px);
		panel.setHeight(height + px);
	}

	@Override
	public void addChild(IsWidget widget, int left, int top) {
		panel.add(widget, left, top);
	}
	
	public IsWidget getView(){
		return this;
	}

	@Override
	public HasWidgets.ForIsWidget getContainer() {
		return panel;
	}
	
	

}
