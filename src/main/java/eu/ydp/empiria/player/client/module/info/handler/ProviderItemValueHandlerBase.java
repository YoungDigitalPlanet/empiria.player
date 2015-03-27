package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public abstract class ProviderItemValueHandlerBase extends ProviderValueHandlerBase {

	@Inject
	public ProviderItemValueHandlerBase(@Assisted SessionDataSupplier sessionDataSupplier) {
		super(sessionDataSupplier);
	}

	@Override
	protected abstract String countValue(ContentFieldInfo info, VariableProviderSocket provider);

	@Override
	protected VariableProviderSocket getVariableProvider(int refItemIndex) {
		return itemSessionDataSocket(refItemIndex).getVariableProviderSocket();
	}

	private ItemSessionDataSocket itemSessionDataSocket(int refItemIndex) {
		return sessionDataSupplier.getItemSessionDataSocket(refItemIndex);
	}

}
