package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ResultValueHandler extends ProviderItemValueHandlerBase {

	@Inject
	public ResultValueHandler(@Assisted SessionDataSupplier sessionDataSupplier) {
		super(sessionDataSupplier);
	}

	@Override
	protected String countValue(ContentFieldInfo info, VariableProviderSocket provider) {
		VariableResult result = new VariableResult(provider);
		return String.valueOf(result.getResult());
	}

}
