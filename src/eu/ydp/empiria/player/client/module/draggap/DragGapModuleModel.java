package eu.ydp.empiria.player.client.module.draggap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;

public class DragGapModuleModel extends AbstractResponseModel<String> {

	public DragGapModuleModel(Response response) {
		super(response);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return new ArrayList<String>(values);
	}
	
	public Response getResponse(){
		return this.response;
	}

}
