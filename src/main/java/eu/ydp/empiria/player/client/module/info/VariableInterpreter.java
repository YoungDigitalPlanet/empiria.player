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

package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.info.InfoModuleContentTokenizer.Token;

import java.util.List;

public class VariableInterpreter {

    @Inject
    private ContentFieldRegistry fieldRegistry;

    public String replaceAllTags(List<Token> tokensFromContent, int refItemIndex) {
        return replaceItemTags(tokensFromContent, refItemIndex);
    }

    private String replaceItemTags(List<Token> tokensFromContent, final int refItemIndex) {
        return Joiner.on("").join(transformTokens(tokensFromContent, refItemIndex));
    }

    private Iterable<String> transformTokens(List<Token> tokensFromContent, final int refItemIndex) {
        return Iterables.transform(tokensFromContent, new Function<Token, String>() {
            @Override
            public String apply(Token token) {
                if (token.isFieldInfo()) {
                    return getFieldInfoValue(refItemIndex, token);
                }
                return token.getName();
            }

        });
    }

    private String getFieldInfoValue(final int refItemIndex, Token token) {
        Optional<ContentFieldInfo> fieldInfo = fieldRegistry.getFieldInfo(token.getName());
        return fieldInfo.isPresent() ? fieldInfo.get().getValue(refItemIndex) : token.getName();
    }

}
