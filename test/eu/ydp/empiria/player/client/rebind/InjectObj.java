package eu.ydp.empiria.player.client.rebind;

import javax.annotation.PostConstruct;

@SuppressWarnings("PMD")
public class InjectObj implements InjectInterface {

	boolean postConstructFire = false;

	public InjectObj(String str) {
	}

	public InjectObj() {
	}

	@PostConstruct
	public void postConstruct() {
		postConstructFire = true;
	}

	@Override
	public boolean isPostConstructFire() {
		return postConstructFire;
	}
}
