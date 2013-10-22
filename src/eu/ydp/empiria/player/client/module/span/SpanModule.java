package eu.ydp.empiria.player.client.module.span;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SpanModule extends SimpleModuleBase implements ISimpleModule, Factory<SpanModule> {

	protected Widget contents;
	private final StyleNameConstants styleNames = eu.ydp.empiria.player.client.PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	
	@Override
	public void initModule(Element element) {
		contents = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineWidget(element);
		contents.setStyleName(styleNames.QP_SPAN());
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
