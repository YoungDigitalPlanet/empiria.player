package eu.ydp.empiria.player.client.style;

import java.util.Map;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.communication.PageReference;

public interface StyleSocket {
	public Map<String,String> getStyles( Element element );
	public void setCurrentPages( PageReference pr );
}
