package eu.ydp.empiria.player.client.module.span;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class SpanModule implements ISimpleModule {

	protected Widget contents;
	
	public SpanModule(){
	}

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		contents = ms.getInlineBodyGeneratorSocket().generateInlineBody(element);
		contents.setStyleName("qp-span");

		String id = element.getAttribute("id");
		if (id != null  &&  !"".equals(id)  &&  getView() != null){
			getView().getElement().setId(id);
		}
	}
	
	@Override
	public Widget getView() {
		return contents;
	}

}
