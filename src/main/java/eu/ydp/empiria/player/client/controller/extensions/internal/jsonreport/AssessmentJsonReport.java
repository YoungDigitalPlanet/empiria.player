package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

import java.util.List;

public class AssessmentJsonReport extends JavaScriptObject {

    protected AssessmentJsonReport() {

    }

    public static AssessmentJsonReport create() {
        return new OverlayTypesParser().get();
    }

    public static AssessmentJsonReport create(String json) {
        return new OverlayTypesParser().get(json);
    }

    public final native void setTitle(String title)/*-{
        this.title = title;
    }-*/;

    public final native void setResult(ResultJsonReport value)/*-{
        this.result = value;
    }-*/;

    public final native void setHints(HintJsonReport value)/*-{
        this.hints = value;
    }-*/;

    public final void setItems(List<ItemJsonReport> items) {
        createItemsArray();
        for (int i = 0; i < items.size(); i++) {
            setItemAt(items.get(i), i);
        }
    }

    public final native void createItemsArray()/*-{
        this.items = [];
    }-*/;

    private final native void setItemAt(ItemJsonReport item, int index)/*-{
        this.items[index] = item;
    }-*/;

    public final String getJSONString() {
        return new JSONObject(this).toString();
    }
}
