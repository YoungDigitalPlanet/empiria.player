package eu.ydp.empiria.player.client.controller.communication;

public class PageDataTest extends PageData {

    public PageDataTest(ItemData[] ids, FlowOptions o, DisplayOptions o2) {
        super(PageType.TEST);
        datas = ids;
        flowOptions = o;
        displayOptions = o2;
    }

    public ItemData[] datas;
    public FlowOptions flowOptions;
    public DisplayOptions displayOptions;
}
