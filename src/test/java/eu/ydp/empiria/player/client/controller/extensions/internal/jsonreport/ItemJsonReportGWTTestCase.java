package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class ItemJsonReportGWTTestCase extends EmpiriaPlayerGWTTestCase {

    public void testGeneratingJSON() {
        ItemJsonReport item = ItemJsonReport.create();
        ResultJsonReport result = ResultJsonReport.create("{\"todo\":2, \"done\":3}");
        HintJsonReport hints = HintJsonReport.create("{\"errors\":2, \"mistakes\":3}");

        item.setIndex(1);
        item.setTitle("Item title");
        item.setResult(result);
        item.setHints(hints);

        assertEquals("JSON String", "{\"index\":1, \"title\":\"Item title\", \"result\":{\"todo\":2, \"done\":3}, \"hints\":{\"errors\":2, \"mistakes\":3}}",
                item.getJSONString());
    }

}
