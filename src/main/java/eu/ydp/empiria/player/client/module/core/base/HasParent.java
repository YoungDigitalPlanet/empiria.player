package eu.ydp.empiria.player.client.module.core.base;

import java.util.List;

public interface HasParent {
    HasChildren getParentModule();

    List<HasChildren> getNestedParents();
}
