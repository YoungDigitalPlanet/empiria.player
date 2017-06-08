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

package eu.ydp.empiria.player.client.module.expression.evaluate;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.expression.ResponseFinder;
import eu.ydp.empiria.player.client.module.expression.adapters.DefaultExpressionCharactersAdapter;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluator implements Evaluator {

    private final ResponseValuesFetcherFunctions fetcherFunctions;
    private final ResponseFinder responseFinder;
    private final DefaultExpressionCharactersAdapter expressionAdapter;

    @Inject
    public CommutationEvaluator(ResponseValuesFetcherFunctions fetcherFunctions, ResponseFinder responseFinder, DefaultExpressionCharactersAdapter expressionAdapter) {
        this.fetcherFunctions = fetcherFunctions;
        this.responseFinder = responseFinder;
        this.expressionAdapter = expressionAdapter;
    }

    @Override
    public boolean evaluate(ExpressionBean bean) {

        Multiset<Multiset<String>> correctAnswers = bean.getCorectResponses();
        Multiset<Multiset<String>> userAnswers = responseFinder.getResponseMultiSet(bean, fetcherFunctions.getUserAnswerFetcher());

        Multiset<Multiset<String>> adaptedUserAnswers = convertSpecialCharacters(userAnswers);
        Multiset<Multiset<String>> adaptedCorrectAnswers = convertSpecialCharacters(correctAnswers);

        return adaptedUserAnswers.equals(adaptedCorrectAnswers);
    }

    private Multiset<Multiset<String>> convertSpecialCharacters(Multiset<Multiset<String>> answers) {
        Multiset<Multiset<String>> modifiedAnswers = HashMultiset.create();

        for (Multiset<String> multiset : answers) {
            Multiset<String> modifiedSubSet = HashMultiset.create();
            for (String string : multiset) {
                String modifiedString = expressionAdapter.process(string);
                modifiedSubSet.add(modifiedString);
            }
            modifiedAnswers.add(modifiedSubSet);
        }
        return modifiedAnswers;
    }
}
