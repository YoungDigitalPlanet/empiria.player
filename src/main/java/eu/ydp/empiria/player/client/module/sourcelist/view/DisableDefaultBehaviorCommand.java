package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.dom.client.NativeEvent;

import eu.ydp.gwtutil.client.event.factory.Command;

public class DisableDefaultBehaviorCommand implements Command {

	@Override
	public void execute(NativeEvent event) {
		event.preventDefault();
	}

}
