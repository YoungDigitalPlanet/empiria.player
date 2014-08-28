package eu.ydp.empiria.player.client;

public class ConsoleLog {

	public static native void alert(String msg) /*-{
		$wnd.logFromEmpiria(msg);
	}-*/;

	public static native void consoleLog(Object obj)/*-{
		console.log(obj);
	}-*/;
}
