package eu.ydp.empiria.player.client.module.order.dndcomponent;

public enum DragMode {
	NONE, FREE, VERTICAL, HORIZONTAL;
	
	public static boolean isSlotManagerEnabled(DragMode dsm){
		return (dsm == VERTICAL  ||  dsm == HORIZONTAL);
	}
}
