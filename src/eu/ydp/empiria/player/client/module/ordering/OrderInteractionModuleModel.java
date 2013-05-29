package eu.ydp.empiria.player.client.module.ordering;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class OrderInteractionModuleModel extends AbstractResponseModel<String>{

	private ResponseModelChangeListener responseModelChange;
	
	@Inject
	public OrderInteractionModuleModel(@ModuleScoped Response response) {
		super(response, null);
	}

	public void initialize(ResponseModelChangeListener modelChangeListener){
		responseModelChange = modelChangeListener;
	}
	
	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return Lists.newArrayList(values);
	}

	public Response getResponse(){
		return this.response;
	}

	public void onModelChange(){
		responseModelChange.onResponseModelChange();
	}
	
	@Override
	public void reset(){
		
	}
}
