package eu.ydp.empiria.player.client.controller.communication;

public class ItemParameters {

	public ItemParameters() {
		modulesCount = 0;
	}

	public ItemParameters(int mc) {
		modulesCount = mc;
	}

	private int modulesCount;

	public void setModulesCount(int mc) {
		modulesCount = mc;
	}

	public int getModulesCount() {
		return modulesCount;
	}
}
