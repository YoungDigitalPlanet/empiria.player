package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public interface FieldValueHandler {

	String getValue(ContentFieldInfo info, int refItemIndex);

}
