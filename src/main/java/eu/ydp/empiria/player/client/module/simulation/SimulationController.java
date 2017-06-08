/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;

@Singleton
public class SimulationController {
    private static final String METHOD_NAME_RESUME_ANIMATION = "resumeAnimation";
    private static final String METHOD_NAME_PAUSE_ANIMATION = "pauseAnimation";
    private static final String METHOD_NAME_WINDOW_RESIZED = "onWindowResized";

    @Inject
    private NativeMethodInvocator methodInvocator;

    public void pauseAnimation(JavaScriptObject context) {
        callMethod(context, METHOD_NAME_PAUSE_ANIMATION);
    }

    public void resumeAnimation(JavaScriptObject context) {
        callMethod(context, METHOD_NAME_RESUME_ANIMATION);
    }

    public void onWindowResized(JavaScriptObject context) {
        callMethod(context, METHOD_NAME_WINDOW_RESIZED);
    }

    private void callMethod(JavaScriptObject context, String methodName) {
        methodInvocator.callMethod(context, methodName);
    }
}
