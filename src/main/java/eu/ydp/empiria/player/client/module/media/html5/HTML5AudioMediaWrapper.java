package eu.ydp.empiria.player.client.module.media.html5;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public class HTML5AudioMediaWrapper extends AbstractHTML5MediaWrapper {

    @Inject
    public HTML5AudioMediaWrapper(@Assisted Media media, EventsBus eventBus, PageScopeFactory pageScopeFactory) {
        super(media, eventBus, pageScopeFactory);
    }

}
