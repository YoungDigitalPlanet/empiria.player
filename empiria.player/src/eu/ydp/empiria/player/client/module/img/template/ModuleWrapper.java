package eu.ydp.empiria.player.client.module.img.template;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.button.MediaController;

public class ModuleWrapper extends MediaController<ModuleWrapper> {

	private static ImgScreenModuleUiBinder uiBinder = GWT.create(ImgScreenModuleUiBinder.class);

	interface ImgScreenModuleUiBinder extends UiBinder<Widget, ModuleWrapper> {
	}

	@UiField
	HTMLPanel container;

	public ModuleWrapper(Widget widget) {
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

	}

}
