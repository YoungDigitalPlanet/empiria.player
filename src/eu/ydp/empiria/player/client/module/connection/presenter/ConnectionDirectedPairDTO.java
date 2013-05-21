package eu.ydp.empiria.player.client.module.connection.presenter;

public class ConnectionDirectedPairDTO {
	private String source;
	private String target;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return source + " " + target;
	}
}