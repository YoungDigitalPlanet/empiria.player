package eu.ydp.empiria.player.client.module.connection;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModuleModel extends AbstractResponseModel<KeyValue<String, String>> {

	@Inject
	public ConnectionModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener modelChangeListener) {
		super(response, modelChangeListener);
	}

	@Override
	protected List<KeyValue<String, String>> parseResponse(Collection<String> values) {
		return null;
	}
	
}
