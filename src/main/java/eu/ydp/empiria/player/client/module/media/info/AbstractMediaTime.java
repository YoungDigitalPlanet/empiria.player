package eu.ydp.empiria.player.client.module.media.info;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaButton;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public abstract class AbstractMediaTime extends AbstractMediaButton {
    NumberFormat formatter = NumberFormat.getFormat("##00");
    @Inject
    protected EventsBus eventsBus;

    public AbstractMediaTime(String baseStyleName) {
        super(baseStyleName);
    }

    /**
     * zwraca tekst w formacie time1:time2 format liczb ##00
     *
     * @param time1
     * @param time2
     * @return
     */
    protected String getInnerText(double time1, double time2) {
        StringBuilder innerText = new StringBuilder(formatter.format(time1));
        innerText.append(":");
        innerText.append(formatter.format(time2));
        return innerText.toString();
    }

    @Override
    protected void onClick() {
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isMediaMetaAvailable() && getMediaAvailableOptions().isSeekSupported();
    }

}
