package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonProvider;
import eu.ydp.gwtutil.client.json.YJsonString;

public class StructureSaver {

	private final YJsonProvider jsonProvider;

	@Inject
	public StructureSaver(YJsonProvider jsonProvider) {
		this.jsonProvider = jsonProvider;
	}

	public YJsonArray save(List<SimpleMatchSetBean> simpleMatchSetBeans) {
		YJsonArray jsonArray = jsonProvider.createYJsonArray();
		for (SimpleMatchSetBean simpleMatchSetBean : simpleMatchSetBeans) {
			jsonArray.set(jsonArray.size(), createAssociableChoices(simpleMatchSetBean));
		}
		return jsonArray;
	}

	private YJsonArray createAssociableChoices(SimpleMatchSetBean simpleMatchSetBean) {
		YJsonArray jsonArray = jsonProvider.createYJsonArray();
		for (SimpleAssociableChoiceBean associableChoiceBean : simpleMatchSetBean.getSimpleAssociableChoices()) {
			String identifier = associableChoiceBean.getIdentifier();
			YJsonString yJsonId = jsonProvider.createString(identifier);
			jsonArray.set(jsonArray.size(), yJsonId);
		}

		return jsonArray;
	}
}
