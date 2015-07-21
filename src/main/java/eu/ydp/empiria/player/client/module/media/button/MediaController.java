package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaController extends SupportedAction, IsWidget {

    /**
     * przekazuje obiekt multimediow na jakim ma pracowac kontrolka
     *
     * @param mediaDescriptor
     */
    public abstract void setMediaDescriptor(MediaWrapper<?> mediaDescriptor);

    /**
     * obiekt opisujacy funkcje dostepne dla podlaczonego zasobu
     *
     * @return
     */
    public abstract MediaAvailableOptions getMediaAvailableOptions();

    /**
     * Zwraca
     *
     * @return
     */
    public abstract MediaWrapper<?> getMediaWrapper();

    public void setFullScreen(boolean fullScreen);

    public Element getElement();

    public void init();

}
