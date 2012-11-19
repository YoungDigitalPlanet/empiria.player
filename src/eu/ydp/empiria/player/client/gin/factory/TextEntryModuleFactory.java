package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.math.InlineChoiceGapModulePresenter;
import eu.ydp.empiria.player.client.module.math.TextEntryGapModulePresenter;
import eu.ydp.empiria.player.client.module.textentry.TextEntryModulePresenter;

public interface TextEntryModuleFactory {
	public TextEntryModulePresenter getTextEntryModulePresenter(@Assisted("imodule") IModule parentModule);
	public TextEntryGapModulePresenter getTextEntryGapModulePresenter(@Assisted("imodule") IModule parentModule);
	public InlineChoiceGapModulePresenter getInlineChoiceGapModulePresenter();
}
