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

package eu.ydp.empiria.player.client.controller.workmode;

import com.google.gwt.i18n.client.Constants;

public interface ModeStyleNameConstants extends Constants {

    @DefaultStringValue("qp-module-mode-preview")
    String QP_MODULE_MODE_PREVIEW();

    @DefaultStringValue("qp-module-mode-test")
    String QP_MODULE_MODE_TEST();

    @DefaultStringValue("qp-module-mode-test-submitted")
    String QP_MODULE_MODE_TEST_SUBMITTED();

}
