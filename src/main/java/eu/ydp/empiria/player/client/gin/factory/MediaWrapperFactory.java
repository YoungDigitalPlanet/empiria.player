package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public interface MediaWrapperFactory {
	public HTML5VideoMediaWrapper getHtml5VideoMediaWrapper(Media media);

	public HTML5AudioMediaWrapper getHtml5AudioMediaWrapper(Media media);
}
