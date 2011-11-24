package eu.ydp.empiria.player.client.controller.session.datasockets;

import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.style.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

public interface ItemSessionDataSocket extends SessionDataSocketBase {
	
	public int getActualTime();
}
