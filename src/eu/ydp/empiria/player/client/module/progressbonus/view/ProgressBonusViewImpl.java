package eu.ydp.empiria.player.client.module.progressbonus.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.model.image.ShowImage;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;

public class ProgressBonusViewImpl extends Composite implements ProgressBonusView {

	private static ProgressBonusViewImplUiBinder uiBinder = GWT.create(ProgressBonusViewImplUiBinder.class);

	@UiTemplate("ProgressBonusView.ui.xml")
	interface ProgressBonusViewImplUiBinder extends UiBinder<Widget, ProgressBonusViewImpl> {
	}

	public ProgressBonusViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	public FlowPanel content;

	@Override
	public void showImage(ShowImageDTO dto) {
		ShowImage showImage = new ShowImage(dto);
		showImage.showOnWidget(content);
	}
}
