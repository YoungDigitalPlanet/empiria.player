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

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.info.InfoModuleContentTokenizer.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleContentTokenizerTest {
    @InjectMocks
    private InfoModuleContentTokenizer instance;

    @Test
    public void getAllTokens_NoInfoTokens() {
        List<Token> allTokens = instance.getAllTokens("string string");
        assertThat(allTokens).isNotEmpty();
    }

    @Test
    public void getAllTokens_InfoTokenOnFirstPosition() {
        String firstToken = "$[string]";
        String lastToken = " string";
        assertThatTokensExistsInCorrectOrder(Lists.newArrayList(firstToken, lastToken));
    }

    @Test
    public void getAllTokens_DollarSignInContent() {
        ArrayList<String> tokens = Lists.newArrayList(" ", "$[a]", "$", "$[q]", " ");
        assertThatTokensExistsInCorrectOrder(tokens);
    }

    @Test
    public void getAllTokens_InfoTokenOnLastPosition() {
        String firstToken = "string ";
        String lastToken = "$[string]";
        assertThatTokensExistsInCorrectOrder(Lists.newArrayList(firstToken, lastToken));
    }

    @Test
    public void getAllTokens() throws Exception {
        final List<String> requiredTokens = Lists.newArrayList(" ", "$[item.title]", "\\", "$[token2]", ". . ", "$[token2]", " -- ");
        assertThatTokensExistsInCorrectOrder(requiredTokens);
    }

    @Test
    public void fieldInfoTokensTest() {
        List<String> infoTokens = Lists.newArrayList("$[item.title]", "$[token2]", "$[token2]");
        List<String> requiredTokens = Lists.newArrayList(" ", "$[item.title]", "\\", "$[token2]", ". . ", "$[token2]", " -- ");
        String contentWithTokens = Joiner.on("").join(requiredTokens);
        Iterable<Token> tokens = instance.getAllTokens(contentWithTokens);
        for (Token token : tokens) {
            if (infoTokens.contains(token.getName())) {
                assertThat(token.isFieldInfo()).isTrue();
            } else {
                assertThat(token.isFieldInfo()).isFalse();
            }
        }
    }

    private void assertThatTokensExistsInCorrectOrder(List<String> requiredTokens) {
        List<Token> allTokens = instance.getAllTokens(Joiner.on("").join(requiredTokens));
        assertThat(allTokens).isNotNull();
        assertThat(allTokens).isNotEmpty();
        assertThat(allTokens).hasSize(requiredTokens.size());
        assertThat(allTokens).doesNotContainNull();
        assertThatAllTokenExists(allTokens, requiredTokens);
        assertTokensOrder(allTokens, requiredTokens);
    }

    private void assertTokensOrder(List<Token> tokens, List<String> requiredTokens) {
        for (int x = 0; x < tokens.size(); ++x) {
            assertThat(tokens.get(x).getName()).isEqualTo(requiredTokens.get(x));
        }
    }

    private void assertThatAllTokenExists(List<Token> tokens, List<String> requiredTokens) {
        for (Token token : tokens) {
            assertThat(token.getName()).isNotNull();
            assertThat(requiredTokens).contains(token.getName());
        }
    }

}
