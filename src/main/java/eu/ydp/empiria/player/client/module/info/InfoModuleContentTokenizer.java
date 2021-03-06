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

import com.google.common.collect.Lists;

import java.util.List;

public class InfoModuleContentTokenizer {
    public class Token {
        private final String stringToken;
        private final boolean filedInfoToken;

        public Token(String stringToken, boolean fieldInfoToken) {
            this.stringToken = stringToken;
            this.filedInfoToken = fieldInfoToken;
        }

        public String getName() {
            return stringToken;
        }

        public boolean isFieldInfo() {
            return filedInfoToken;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Token [stringToken=").append(stringToken).append(", filedInfoToken=").append(filedInfoToken).append("]");
            return builder.toString();
        }

    }

    public List<Token> getAllTokens(String contentWithTokens) {
        List<Token> allStringTokens = Lists.newArrayList();
        if (hasInfoFiledToken(contentWithTokens)) {
            parseAndAddTokens(contentWithTokens, allStringTokens);
        } else {
            addNoInfoToken(allStringTokens, contentWithTokens);
        }
        return allStringTokens;
    }

    private void parseAndAddTokens(String contentWithTokens, List<Token> allStringTokens) {
        List<Token> allTokens = getTokensFromString(contentWithTokens);
        allStringTokens.addAll(allTokens);
    }

    private boolean hasInfoFiledToken(String contentWithTokens) {
        return contentWithTokens.contains("$[");
    }

    /**
     * NO regex to speedup report generation
     *
     * @return
     */
    private List<Token> getTokensFromString(String token) {
        int tokenStart = 0;
        boolean inInfoToken = false;
        List<Token> resultTokens = Lists.newArrayList();
        for (int x = 0; x < token.length(); ++x) {
            if (isStartOfInfoToken(token, x)) {
                if (wasTextTokenBetweenLastInfoTokenAndThisToken(tokenStart, x)) {
                    String tokenString = token.substring(tokenStart, x);
                    addNoInfoToken(resultTokens, tokenString);
                }
                tokenStart = x;
                inInfoToken = true;
            } else {
                if (isEndOfInfoToken(token, inInfoToken, x)) {
                    String tokenString = token.substring(tokenStart, x + 1);
                    addInfoToken(resultTokens, tokenString);
                    tokenStart = x + 1;
                    inInfoToken = false;
                }
            }
        }
        if (tokenStart < token.length()) {
            resultTokens.add(new Token(token.substring(tokenStart), false));
        }
        return resultTokens;
    }

    private boolean wasTextTokenBetweenLastInfoTokenAndThisToken(int lastTokenStart, int x) {
        return x > 0 && lastTokenStart != x;
    }

    private void addInfoToken(List<Token> resultTokens, String tokenString) {
        resultTokens.add(new Token(tokenString, true));
    }

    private void addNoInfoToken(List<Token> resultTokens, String tokenString) {
        resultTokens.add(new Token(tokenString, false));
    }

    private boolean isStartOfInfoToken(String token, int x) {
        return token.charAt(x) == '$';
    }

    private boolean isEndOfInfoToken(String token, boolean inInfoToken, int x) {
        return inInfoToken && token.charAt(x) == ']';
    }
}
