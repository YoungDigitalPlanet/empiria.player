package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

public abstract class InlineModuleBase extends SingleViewModuleBase implements IInlineModule {
	
	@Override
	public final void initModule(Element element, ModuleSocket ms) {
		super.initModuleInternal(element, ms);
	}
	
}
