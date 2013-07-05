package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class NativeDragDataObject extends JavaScriptObject implements DragDataObject {
	protected NativeDragDataObject() {
		//
	}

	@Override
	public final native String getItemId()/*-{
		return this.itemId;
	}-*/;

	@Override
	public native void setItemId(String itemId)/*-{
		this.itemId = itemId;
	}-*/;

	@Override
	public final native String getSourceId() /*-{
		return this.sourceId;
	}-*/;

	@Override
	public native void setSourceId(String sourceId) /*-{
		 this.sourceId = sourceId;
	}-*/;

	@Override
	public String toJSON() {
		return new JSONObject(this).toString();
	}

}
