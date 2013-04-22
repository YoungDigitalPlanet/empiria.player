package eu.ydp.empiria.player.client.module.connection;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.conversion.StateToStateAndStructureConverter;
import eu.ydp.gwtutil.client.state.AbstractStateHelper;
import eu.ydp.gwtutil.client.state.converter.StateConverter;

public class InteractionModuleVersionConverter extends AbstractStateHelper {

	@Inject
	private StateToStateAndStructureConverter stateAndStructureConverter;

	@Override
	protected List<StateConverter> prepareStateConverters() {
		List<StateConverter> converters = new ArrayList<StateConverter>();
		converters.add(new StateConverter(stateAndStructureConverter));
		return converters;
	}

	@Override
	public int getTargetVersion() {
		return 1;
	}

}
