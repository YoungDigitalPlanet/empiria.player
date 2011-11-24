package eu.ydp.empiria.player.client.controller.messages;

public class OperationMessagePoint {

	private static OperationMessageManager omm = new OperationMessageManager();
	
	public static void showMessage(OperationMessage om){
		omm.showMessage(om);
	}

	public static void hideMessage(OperationMessage om){
		omm.hideMessage(om);
	}
}
