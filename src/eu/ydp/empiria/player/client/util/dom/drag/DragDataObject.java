package eu.ydp.empiria.player.client.util.dom.drag;

public interface DragDataObject {

	public String getPreviousValue();

	public String getSourceId();

	public String getValue();

	public String toJSON();

	public void setValue(String value);

	public void setPreviousValue(String value);

	public void setSourceId(String sId);

}
