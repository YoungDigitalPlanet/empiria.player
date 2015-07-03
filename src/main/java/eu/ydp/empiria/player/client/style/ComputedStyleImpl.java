package eu.ydp.empiria.player.client.style;

public class ComputedStyleImpl implements ComputedStyle {

    @Override
    public native String getDirectionFromBody()/*-{
        return $wnd.getComputedStyle($doc.body, null).getPropertyValue("direction");
    }-*/;

}
