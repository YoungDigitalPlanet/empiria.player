package eu.ydp.empiria.player.client.util.dom.drag.emulate;

import com.google.gwt.dom.client.DataTransfer;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Element;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public abstract class AbstractHTML5DragDropWrapper implements DragDropSetGetData{

	protected static final String JSON = "data-json";


	protected static OverlayTypesParser parser = new OverlayTypesParser();

	JsonAttr attr = parser.get();

	public void setJsonAttr(JsonAttr jsonAttr) {
		this.attr = jsonAttr;
	}
	@Override
	public void clearData() {
		attr = parser.get();
	}

	@Override
	public void cleatData(String format) {
		attr.setAttrValue(getKey(format), "");
	}

	private String getKey(String format) {
		return format.replaceAll("[^A-Za-z_]", "");
	}


	@Override
	public void setData(String format, String data) {
		attr.setAttrValue(getKey(format), data);
		if(getElement()!=null){
			getElement().setAttribute(JSON, new JSONObject(attr).toString());
		}
	}


	@Override
	public String getData(String format) {
		return attr.getAttrValue(getKey(format));
	}


	@Override
	public DataTransfer getDataTransfer(){
		return getDataTransfer(this);
	};

	public native DataTransfer getDataTransfer(DragDropSetGetData dataObject)/*-{
		return {
			clearData : function(format) {
				dataObject.@eu.ydp.empiria.player.client.util.dom.drag.emulate.DragDropSetGetData::cleatData(Ljava/lang/String;)(format);
			},
			clearData : function() {
				dataObject.@eu.ydp.empiria.player.client.util.dom.drag.emulate.DragDropSetGetData::cleatData(Ljava/lang/String;)();
			},
			getData : function(format) {
				dataObject.@eu.ydp.empiria.player.client.util.dom.drag.emulate.DragDropSetGetData::getData(Ljava/lang/String;)(format);
			},
			setData : function(format, data) {
				dataObject.@eu.ydp.empiria.player.client.util.dom.drag.emulate.DragDropSetGetData::setData(Ljava/lang/String;Ljava/lang/String;)(format,data);
			},
			setDragImage : function(element, x, y) {

			}
		}
	}-*/;

	protected abstract Element getElement();
}
