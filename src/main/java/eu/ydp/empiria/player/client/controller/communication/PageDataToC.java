package eu.ydp.empiria.player.client.controller.communication;

public class PageDataToC extends PageData {

    public PageDataToC(String[] ts) {
        super(PageType.TOC);
        titles = ts;
    }

    public String[] titles;

}
