package eu.ydp.empiria.player.client.module.span;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class SpanModule extends SimpleModuleBase implements ISimpleModule, Factory<SpanModule> {

	protected Widget contents;

	@Override
	public void initModule(Element element) {
		contents = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element);
		contents.setStyleName("qp-span");		
	}

	@Override
	public Widget getView() {
		return contents;
	}

	@Override
	public SpanModule getNewInstance() {
		return new SpanModule();
	}

}
