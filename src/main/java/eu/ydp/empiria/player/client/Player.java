package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.version.Version;
import eu.ydp.empiria.player.client.view.ViewEngine;

/**
 * Main class with player API
 *
 */
public class Player {

    /**
     * Delivery engine do manage the assessment content
     */
    public DeliveryEngine deliveryEngine;

    private final IPlayerContainersAccessor accessor;

    {
        logVersion();
    }

    @Inject
    public Player(@Assisted String id, @Assisted JavaScriptObject jsObject, ViewEngine viewEngine, DeliveryEngine deliveryEngine,
                  IPlayerContainersAccessor accessor) {
        this.accessor = accessor;
        this.deliveryEngine = deliveryEngine;
        try {
            RootPanel.get(id);
        } catch (Exception e) {
        }
        RootPanel root = RootPanel.get(id);
        viewEngine.mountView(root);
        getAccessor().setPlayerContainer(root);

        deliveryEngine.init(jsObject);
    }

    public void loadExtension(JavaScriptObject extension) {
        deliveryEngine.loadExtension(extension);
    }

    public void loadExtension(String extension) {
        deliveryEngine.loadExtension(extension);
    }

    public void load(String url) {
        deliveryEngine.load(url);
    }

    public void load(XmlData assessmentData, XmlData[] itemsData) {
        deliveryEngine.load(assessmentData, itemsData);
    }

    private void logVersion() {
        String version = Version.getVersion();
        String versionMessage = "EmpiriaPlayer ver. " + version;
        log(versionMessage);
        System.out.println(versionMessage);
    }

    public static native void log(Object message)/*-{
            console.log(message);
    }-*/;

    private IPlayerContainersAccessor getAccessor() {
        return accessor;
    }
}
