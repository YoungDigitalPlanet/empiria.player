package eu.ydp.empiria.player.client.util.dom.drag;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class NativeDragDataObject extends JavaScriptObject implements DragDataObject {
	protected NativeDragDataObject() {
		//
	}

	@Override
	public final native String getSourceId()/*-{
		return this.sourceId;
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
	public final native void setSourceId(String sourceId)/*-{
		this.sourceId = sourceId;
	}-*/;

	@Override
	public final String toJSON(){
		return new JSONObject(this).toString();
	}

}
