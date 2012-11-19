package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class NativeDragDataObject extends JavaScriptObject implements DragDataObject {
	protected NativeDragDataObject() {
		//
	}

	@Override
	public final native String getSourceId()/*-{
		return this.id;
	}-*/;

	@Override
	public final native void setSourceId(String sId) /*-{
		this.id = sId;
	}-*/;

	@Override
	public final native String getPreviousValue()/*-{
		return this.previousValue;
	}-*/;

	@Override
	public final native String getValue()/*-{
		return this.value;
	}-*/;

	@Override
	public final native void setValue(String value)/*-{
		this.value = value;
	}-*/;

	@Override
	public final native void setPreviousValue(String value)/*-{
		this.previousValue = value;
	}-*/;

	@Override
	public final String toJSON() {
		return new JSONObject(this).toString();
	}

}
