package eu.ydp.empiria.player.client.module.video.view;

import java.util.Collection;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.video.structure.SourceBean;

public interface VideoPlayer extends IsWidget {

	void setSkinName(String skinName);

	void setControls(boolean controls);

	void setPreload(String preload);

	void addSource(SourceBean source);

	void addSources(Collection<SourceBean> sources);

	void setPoster(String poster);

	void setWidth(int width);

	void setHeight(int height);
}