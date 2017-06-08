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

package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.Wrapper;

public class TextBoxExpressionReplacer {

    @Inject
    private Provider<ReplacingChangeHandler> handlerProvider;

    public void makeReplacements(final TextBox textBox, final ExpressionReplacer replacer) {
        ReplacingChangeHandler handler = handlerProvider.get();
        handler.init(Wrapper.of(textBox), replacer);
    }

}
