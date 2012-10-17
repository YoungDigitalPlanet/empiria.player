package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.choice.ChoiceModule;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;

public interface ChoiceModuleFactory {
	public ChoiceModuleListener getChoiceModuleListener(ChoiceModule module);
	public ChoiceModuleStructure getChoiceModuleStructure();
}
