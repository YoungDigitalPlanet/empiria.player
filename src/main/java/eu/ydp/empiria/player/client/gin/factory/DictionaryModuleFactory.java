package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.DescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.EntryDescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationDescriptionSoundController;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.jsfilerequest.client.FileRequestCallback;

public interface DictionaryModuleFactory {

	FileRequestCallback createFileRequestCallback(int index, boolean playSound);

	ExplanationDescriptionSoundController getExplanationDescriptionSoundController(ExplanationView view);

	EntryDescriptionSoundController geEntryDescriptionSoundController(ExplanationView view);
}
