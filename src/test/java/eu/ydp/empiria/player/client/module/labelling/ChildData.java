package eu.ydp.empiria.player.client.module.labelling;

public class ChildData {
	private int x;
	private int y;
	private String modulesXml;

	public ChildData(int x, int y, String xml) {
		super();
		this.x = x;
		this.y = y;
		this.modulesXml = xml;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getModulesXml() {
		return modulesXml;
	}

	public String xml() {
		return "<child x='" + x + "' y='" + y + "'>" + modulesXml + "</child>";
	}
}