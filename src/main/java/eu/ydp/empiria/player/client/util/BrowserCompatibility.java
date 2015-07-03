package eu.ydp.empiria.player.client.util;

public abstract class BrowserCompatibility {

    public static native boolean detectIPhone()/*-{
        var uagent = navigator.userAgent.toLowerCase();
        var deviceIphone = "iphone";
        var deviceIpod = "ipod";
        var deviceIpad = "ipad";

        if (uagent.search(deviceIphone) > -1)
            return true;
        if (uagent.search(deviceIpod) > -1)
            return true;
        if (uagent.search(deviceIpad) > -1)
            return true;

        return false;
    }-*/;
}
