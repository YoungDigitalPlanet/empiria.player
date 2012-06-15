package eu.ydp.empiria.player.client.debug;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Debug {
	private static Logger _debug = null;
	
	public static void log(Object text) {
		if (_debug == null)
		{
			_debug = GWT.create(Logger.class);
			
			if (_debug instanceof IsWidget)
				RootPanel.get().add((Widget) _debug);
		}
		
		_debug.log(String.valueOf(text.toString()));
	}
}
