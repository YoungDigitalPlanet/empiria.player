package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;

public class ContentFieldInfoFactory {

	public ContentFieldInfo create(String tagName, FieldValueHandler handlerValueHandler) {
		ContentFieldInfo contentFieldInfo = new ContentFieldInfo();
		contentFieldInfo.setTagName(tagName);
		contentFieldInfo.setHandler(handlerValueHandler);
		return contentFieldInfo;
	}
}
