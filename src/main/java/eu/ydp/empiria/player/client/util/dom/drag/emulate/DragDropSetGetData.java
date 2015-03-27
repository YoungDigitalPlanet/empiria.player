package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.DataTransfer;

public interface DragDropSetGetData {
	public void setData(String format, String data);

	public String getData(String format);

	public void clearData();

	public void cleatData(String format);

	public DataTransfer getDataTransfer();
}
