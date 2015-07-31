package eu.ydp.empiria.player.client;

import com.google.gwt.junit.client.GWTTestCase;

public abstract class EmpiriaPlayerGWTTestCase extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "eu.ydp.empiria.player.Player";
    }

}
