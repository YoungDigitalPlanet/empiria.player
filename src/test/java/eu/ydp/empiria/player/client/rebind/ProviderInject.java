package eu.ydp.empiria.player.client.rebind;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ProviderInject {

	@Inject
	Provider<InjectObj> inject;

	public InjectObj getInject() {
		return inject.get();
	}
}
