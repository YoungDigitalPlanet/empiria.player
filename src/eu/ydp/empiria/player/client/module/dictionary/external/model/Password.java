package eu.ydp.empiria.player.client.module.dictionary.external.model;

public class Password {

	private final String password;
	private final int index;

	public Password(String password, int index) {
		this.password = password;
		this.index = index;
	}

	public String getPassword() {
		return password;
	}

	public int getIndex() {
		return index;
	}

}
