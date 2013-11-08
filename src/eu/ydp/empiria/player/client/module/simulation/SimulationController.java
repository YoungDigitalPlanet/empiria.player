package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.json.NativeMethodInvocator;

public class SimulationController {
	private static final String METHOD_NAME_RESUME_ANIMATION = "resumeAnimation";
	private static final String METHOD_NAME_PAUSE_ANIMATION = "pauseAnimation";

	@Inject
	private NativeMethodInvocator methodInvocator;

	public void pauseAnimation(JavaScriptObject context) {
		callMethod(context, METHOD_NAME_PAUSE_ANIMATION);
	}

	public void resumeAnimation(JavaScriptObject context) {
		callMethod(context, METHOD_NAME_RESUME_ANIMATION);
	}

	private void callMethod(JavaScriptObject context, String methodName) {
		methodInvocator.callMethod(context, methodName);
	}
}
