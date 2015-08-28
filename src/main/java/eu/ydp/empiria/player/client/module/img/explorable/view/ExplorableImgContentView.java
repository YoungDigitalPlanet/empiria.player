package eu.ydp.empiria.player.client.module.img.explorable.view;


import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;

public interface ExplorableImgContentView {

    void init(Element element, ModuleSocket moduleSocket, ImageProperties properties);

    void zoomIn();

    void zoomOut();

    void registerCommandOnToolbox(EventHandlerProxy handler);

    void registerZoomInButtonCommands(EventHandlerProxy handler);

    void registerZoomOutButtonCommands(EventHandlerProxy handler);

}
