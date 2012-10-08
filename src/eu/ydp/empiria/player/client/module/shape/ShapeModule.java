package eu.ydp.empiria.player.client.module.shape;

import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class ShapeModule extends SimpleModuleBase implements Factory<ShapeModule> {

	protected PushButton button;

	public ShapeModule(){
		button = new PushButton();
		button.setStyleName("qp-shape");
	}

	@Override
	public void initModule(Element element) {
	}

	@Override
	public Widget getView() {
		return button;
	}

	@Override
	public ShapeModule getNewInstance() {
		return new ShapeModule();
	}

}
