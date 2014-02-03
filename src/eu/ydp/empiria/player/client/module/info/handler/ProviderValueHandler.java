package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableUtil;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ProviderValueHandler extends ProviderItemValueHandlerBase {

	private static final String DEFAULT_VALUE = "0";

	@Inject
	public ProviderValueHandler(@Assisted SessionDataSupplier sessionDataSupplier) {
		super(sessionDataSupplier);
	}

	@Override
	protected String countValue(ContentFieldInfo info, VariableProviderSocket provider) {
		VariableUtil util = new VariableUtil(provider);
		return util.getVariableValue(info.getValueName(), DEFAULT_VALUE);
	}

}
