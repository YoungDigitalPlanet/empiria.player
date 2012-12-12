package eu.ydp.empiria.player.client.module.selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class SelectionModuleModel extends AbstractResponseModel<String> {

	@Inject
	public SelectionModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener modelChangeListener) {
		super(response, modelChangeListener);
	}
	
	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return new ArrayList<String>(values);
	}

}
