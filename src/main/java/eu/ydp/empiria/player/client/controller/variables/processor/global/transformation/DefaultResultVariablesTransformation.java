package eu.ydp.empiria.player.client.controller.variables.processor.global.transformation;

import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResponseResultVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

public class DefaultResultVariablesTransformation implements Function<Entry<Response, DtoModuleProcessingResult>, ResultVariables> {

	@Override
	@Nullable
	public ResultVariables apply(@Nullable Entry<Response, DtoModuleProcessingResult> input) {
		return new ResponseResultVariables(input.getValue());
	}
}