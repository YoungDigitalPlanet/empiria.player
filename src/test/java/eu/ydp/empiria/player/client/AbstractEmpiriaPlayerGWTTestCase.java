package eu.ydp.empiria.player.client;

import com.google.gwt.junit.client.GWTTestCase;

public abstract class AbstractEmpiriaPlayerGWTTestCase extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "eu.ydp.empiria.player.Player";
    }

}
