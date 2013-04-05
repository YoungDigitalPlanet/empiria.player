package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.json.NativeMethodInvocator;

public class SimulationController {
	@Inject
	private NativeMethodInvocator methodInvocator;

	public void pauseAnimation(JavaScriptObject context){
		callMethod(context, "pauseAnimation");
	}

	public void resumeAnimation(JavaScriptObject context){
		callMethod(context, "resumeAnimation");
	}

	private void callMethod(JavaScriptObject context,String methodName) {
		if(context!=null && methodName!=null) {
			methodInvocator.callMethod(context, methodName);
		}
	}
}
