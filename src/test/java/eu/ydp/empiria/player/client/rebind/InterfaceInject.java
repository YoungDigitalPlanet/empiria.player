package eu.ydp.empiria.player.client.rebind;

import com.google.inject.Inject;

@SuppressWarnings("PMD")
public class InterfaceInject {

	@Inject
	InjectInterface inject;

	public InterfaceInject() {
	}

	public InterfaceInject(String module) {
	}

	public InjectInterface getInject() {
		return inject;
	}

}
