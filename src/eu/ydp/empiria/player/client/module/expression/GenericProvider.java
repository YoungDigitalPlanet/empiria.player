package eu.ydp.empiria.player.client.module.expression;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GenericProvider<T> implements Provider<T> {

	@Inject
	Provider<T> provider;
	
	@Override
	public T get() {
		return provider.get();
	}

}
