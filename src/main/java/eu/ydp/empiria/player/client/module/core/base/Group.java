package eu.ydp.empiria.player.client.module.core.base;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.core.flow.Activity;

public interface Group extends Activity {
    public GroupIdentifier getGroupIdentifier();
}
