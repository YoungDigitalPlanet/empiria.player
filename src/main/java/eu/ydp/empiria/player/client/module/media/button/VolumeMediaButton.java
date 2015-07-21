package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;

public class VolumeMediaButton extends AbstractMediaButton {
    public VolumeMediaButton() {
        super("qp-media-volume");
    }

    @Inject
    private VolumeScrollBar volumeScrollBar;
    boolean attached = false;

    @Override
    public void init() {
        super.init();
        if (isSupported()) {
            volumeScrollBar.setMediaDescriptor(getMediaWrapper());
            volumeScrollBar.init();
            volumeScrollBar.setVisible(false);

        }
    }

    @Override
    protected void onClick() {
        setActive(!isActive());
        if (!attached) {
            volumeScrollBar.getElement().getStyle().setPosition(Position.ABSOLUTE);
            ((Panel) getParent()).add(volumeScrollBar);
            attached = true;
        }
        changeStyleForClick();
        if (isActive()) {
            volumeScrollBar.setVisible(true);
            int width = volumeScrollBar.getElement().getAbsoluteRight() - volumeScrollBar.getElement().getAbsoluteLeft();
            width = getElement().getAbsoluteRight() - getElement().getAbsoluteLeft() - width;
            volumeScrollBar.getElement().getStyle().setLeft(getElement().getAbsoluteLeft() + width / 2, Unit.PX);
            int height = volumeScrollBar.getElement().getAbsoluteBottom() - volumeScrollBar.getElement().getAbsoluteTop();
            volumeScrollBar.getElement().getStyle().setTop(getElement().getAbsoluteTop() - height, Unit.PX);

        } else {
            volumeScrollBar.setVisible(false);
        }
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isVolumeChangeSupported();
    }
}
