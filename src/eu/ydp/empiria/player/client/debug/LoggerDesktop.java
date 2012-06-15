package eu.ydp.empiria.player.client.debug;


public class LoggerDesktop implements Logger {
	
	public native void log(String text) /*-{
		console.log(text);
	}-*/;

}
