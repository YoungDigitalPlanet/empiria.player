package eu.ydp.empiria.player.client.module.img.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;

public class ModuleWrapper extends AbstractMediaController<ModuleWrapper> {

	private static ImgScreenModuleUiBinder uiBinder = GWT.create(ImgScreenModuleUiBinder.class);

	interface ImgScreenModuleUiBinder extends UiBinder<Widget, ModuleWrapper> {
	}

	@UiField
	protected HTMLPanel container;

	public ModuleWrapper(IsWidget widget) {
		initWidget(uiBinder.createAndBindUi(this));
		container.add(widget);
	}


	@Override
	public ModuleWrapper getNewInstance() {
		return null;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public void init() {
		//
	}

	@Override
	public void setStyleNames() {
		//
	}
}
