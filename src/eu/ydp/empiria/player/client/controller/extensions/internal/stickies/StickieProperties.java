package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import java.util.Date;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public class StickieProperties {

	/**
	 * color index
	 */
	int colorIndex;
	String stickieTitle;
	String stickieContent;
	int x, y;
	long timestamp;
	boolean minimized;
	
	public StickieProperties(int colorIndex, String stickieTitle, int x, int y) {
		super();
		this.colorIndex = colorIndex;
		setStickieTitle(stickieTitle);
		this.x = x;
		this.y = y;
		this.minimized = false;
		this.stickieContent = "";
		updateTimestamp();
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	public String getStickieTitle() {
		return this.stickieTitle;
	};

	public  void setStickieTitle(String stickieTitle) {
		this.stickieTitle = stickieTitle;
	};

	public String getStickieContent() {
		return stickieContent;
	}

	public void setStickieContent(String bookmarkContent) {
		this.stickieContent = bookmarkContent;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void updateTimestamp() {
		timestamp = new Date().getTime();
	}

	public boolean getMinimized() {
		return minimized;
	}

	public void setMinimized(boolean minimized) {
		this.minimized = minimized;
	}

	public JSONValue toJSON() {
		JSONArray arr = new JSONArray();
		arr.set(0, new JSONNumber(colorIndex));
		arr.set(1, new JSONString(getStickieTitle()));
		arr.set(2, new JSONString(stickieContent));
		arr.set(3, new JSONNumber(x));
		arr.set(4, new JSONNumber(y));
		arr.set(5, new JSONNumber(timestamp));
		arr.set(6, JSONBoolean.getInstance(minimized));
		return arr;
	}
	
	public static StickieProperties fromJSON(JSONValue json){
		JSONArray arr = json.isArray();
		int index = (int) arr.get(0).isNumber().doubleValue();
		String title = arr.get(1).isString().stringValue();
		String content = arr.get(2).isString().stringValue();
		int x = (int)json.isArray().get(3).isNumber().doubleValue();
		int y = (int)json.isArray().get(4).isNumber().doubleValue();
		long timestamp = (long)json.isArray().get(5).isNumber().doubleValue();
		boolean minimized = json.isArray().get(6).isBoolean().booleanValue();
		StickieProperties sp = new StickieProperties(index, title, x, y);
		sp.stickieContent = content;
		sp.timestamp = timestamp;
		sp.minimized = minimized;
		return sp;
	}
}
