package eu.ydp.empiria.player.client.module.img.explorable.view;


import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;

public interface ExplorableImgContentView {

    void zoomIn();

    void zoomOut();

    void registerCommandOnToolbox(EventHandlerProxy handler);

    void registerZoomInButtonCommands(EventHandlerProxy handler);

    void registerZoomOutButtonCommands(EventHandlerProxy handler);

}
