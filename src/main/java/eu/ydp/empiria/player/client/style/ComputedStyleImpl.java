package eu.ydp.empiria.player.client.style;

import com.google.inject.Singleton;

@Singleton
public class ComputedStyleImpl implements ComputedStyle {

    @Override
    public native String getDirectionFromBody()/*-{
        return $wnd.getComputedStyle($doc.body, null).getPropertyValue("direction");
    }-*/;

}
