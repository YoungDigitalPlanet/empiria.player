package eu.ydp.empiria.player.client.controller.communication;

public class PageReference {

    public PageReference(PageType t, int[] pInd, FlowOptions o, DisplayOptions o2) {
        type = t;
        pageIndices = pInd;
        flowOptions = o;
        displayOptions = o2;
    }

    public PageType type;
    public int[] pageIndices;
    public FlowOptions flowOptions;
    public DisplayOptions displayOptions;
}
