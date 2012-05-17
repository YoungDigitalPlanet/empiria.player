package eu.ydp.empiria.player.client.module.shape;

import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class ShapeModule implements ISimpleModule,Factory<ShapeModule> {

	protected PushButton button;

	public ShapeModule(){
		button = new PushButton();
		button.setStyleName("qp-shape");
	}

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {

		String cls = element.getAttribute("class");
		if (cls != null)
			button.addStyleName(cls);
		String id = element.getAttribute("id");
		if (id != null  &&  !"".equals(id)  &&  getView() != null){
			button.getElement().setId(id);
		}
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
