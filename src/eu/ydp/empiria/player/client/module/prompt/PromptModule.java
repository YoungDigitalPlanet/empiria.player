package eu.ydp.empiria.player.client.module.prompt;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class PromptModule implements ISimpleModule,Factory<PromptModule> {

	protected Widget contents;

	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		contents = ms.getInlineBodyGeneratorSocket().generateInlineBody(element);
		contents.setStyleName("qp-prompt");

		String id = element.getAttribute("id");
		if (id != null  &&  !"".equals(id)  &&  getView() != null){
			contents.getElement().setId(id);
		}
		String cls = element.getAttribute("class");
		if (cls != null)
			contents.addStyleName(cls);
	}

	@Override
	public Widget getView() {
		return contents;
	}

	@Override
	public PromptModule getNewInstance() {
		return new PromptModule();
	}

}
