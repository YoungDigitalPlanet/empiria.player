package eu.ydp.empiria.player.client.module.simple.simpletext;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class SimpleTextModule implements ISimpleModule {

	protected Widget contents;
	
	public SimpleTextModule(){
		contents = new ElementWrapperWidget(Document.get().createPElement());
		contents.setStyleName("qp-simpletext");
	}

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		ms.getInlineBodyGeneratorSocket().generateInlineBody(element, contents.getElement());

		String className = element.getAttribute("class");
		if (className != null  &&  !"".equals(className)  &&  getView() != null){
			getView().addStyleName(className);
		}
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
