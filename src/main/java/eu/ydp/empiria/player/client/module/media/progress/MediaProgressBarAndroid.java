package eu.ydp.empiria.player.client.module.media.progress;

import com.google.gwt.dom.client.NativeEvent;

import javax.annotation.PostConstruct;

public class MediaProgressBarAndroid extends MediaProgressBarImpl {

    @Override
    @PostConstruct
    public void postConstruct() {
        super.postConstruct();
    }

    @Override
    public MediaProgressBarImpl getNewInstance() {
        return new MediaProgressBarAndroid();
    }

    @Override
    protected void setPosition(NativeEvent event) {
        if (isAttached()) {
            event.preventDefault();
            int positionX = getPositionX(event);
            moveScroll(positionX > 0 ? positionX : 0, true);
            if (!isPressed()) {// robimy seeka tylko gdy zakonczono dotyk
                seekInMedia(positionX > 0 ? positionX : 0);
            }
        }
    }
}
