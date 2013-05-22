package eu.ydp.empiria.player.client.module.ordering;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class OrderInteractionModuleModel extends AbstractResponseModel<String>{

	@Inject
	public OrderInteractionModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener responseModelChange) {
		super(response, responseModelChange);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return Lists.newArrayList(values);
	}

	public Response getResponse(){
		return this.response;
	}
	
	public void swicthAnswers(String firstAnswer, String secondAnswer){
		List<String> currentAnswers = getCurrentAnswers();
		
		int indexOfFirstAnswer = currentAnswers.indexOf(firstAnswer);
		int indexOfSecondAnswer = currentAnswers.indexOf(secondAnswer);
		
		currentAnswers.set(indexOfFirstAnswer, secondAnswer);
		currentAnswers.set(indexOfSecondAnswer, firstAnswer);
	}
}
