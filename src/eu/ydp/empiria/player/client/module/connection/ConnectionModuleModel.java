package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModuleModel extends AbstractResponseModel<KeyValue<String, String>> {

	@Inject
	public ConnectionModuleModel(@Assisted Response response) {
		super(response);
	}
	
}
