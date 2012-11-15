package eu.ydp.empiria.player.client.module;

import java.util.List;

public interface IModule extends HasParent {
	List<IModule> getChildren();
}
