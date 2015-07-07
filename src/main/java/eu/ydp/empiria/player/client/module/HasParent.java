package eu.ydp.empiria.player.client.module;

import java.util.List;

public interface HasParent {
    HasChildren getParentModule();

    List<HasChildren> getNestedParents();
}
