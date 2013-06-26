package eu.ydp.empiria.player.client.util.dom.drag;

public interface DragDataObject {

	public String getItemId();

	public String getSourceId();

	public String toJSON();

	public void setItemId(String value);

	public void setSourceId(String value);

}
