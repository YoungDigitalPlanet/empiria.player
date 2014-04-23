package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.jsfilerequest.client.FileRequestCallback;

public interface DictionaryModuleFactory {

	FileRequestCallback createFileRequestCallback(int index, boolean playSound);
}
