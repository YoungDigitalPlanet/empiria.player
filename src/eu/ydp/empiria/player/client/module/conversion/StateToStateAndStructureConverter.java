package eu.ydp.empiria.player.client.module.conversion;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.structure.StateController;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonObject;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.state.converter.IStateConvertionStrategy;

public class StateToStateAndStructureConverter implements IStateConvertionStrategy {

	private final IJSONService jsonService;

	@Inject
	public StateToStateAndStructureConverter(IJSONService jsonService) {
		this.jsonService = jsonService;
	}

	@Override
	public int getStartVersion() {
		return 0;
	}

	@Override
	public YJsonValue convert(YJsonValue jsonState) {

		if (!isVersionForConvert(jsonState)) {
			return jsonState;
		}

		YJsonObject resultObject = jsonService.createObject();

		resultObject.put(StateController.STRUCTURE, jsonService.createArray());
		resultObject.put(StateController.STATE, jsonState);

		YJsonArray result = jsonService.createArray();
		result.set(0, resultObject);
		return result;
	}

	private boolean isVersionForConvert(YJsonValue jsonState) {
		return jsonState.isArray().size() == 0 || jsonState.isArray().get(0).isObject() == null;
	}
}
