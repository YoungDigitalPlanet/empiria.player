package eu.ydp.empiria.player.client.rebind;

import com.google.inject.Inject;

@SuppressWarnings("PMD")
public class SimpleInject {

	@Inject
	InjectObj inject;

	public SimpleInject() {
	}

	public SimpleInject(String module) {
	}

	public InjectObj getInject() {
		return inject;
	}

}
