package eu.ydp.empiria.player.client.module.inlinechoice;

import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IUniqueModule;

public interface InlineChoiceController extends IInteractionModule {

	public void setShowEmptyOption(boolean seo);
	public void setParentModule(IUniqueModule module);
}
