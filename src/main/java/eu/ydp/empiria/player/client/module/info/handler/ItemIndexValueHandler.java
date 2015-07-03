package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ItemIndexValueHandler implements FieldValueHandler {

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        return String.valueOf(refItemIndex + 1);
    }

}
