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

import com.google.common.base.Function;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResponseFinder {

    private final ExpressionToPartsDivider expressionToPartsDivider;
    private final IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;

    @Inject
    public ResponseFinder(ExpressionToPartsDivider expressionToPartsDivider, IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor) {
        this.expressionToPartsDivider = expressionToPartsDivider;
        this.identifiersFromExpressionExtractor = identifiersFromExpressionExtractor;
    }

    public Set<Response> getResponsesByIds(List<String> responseIdentifiers, List<Response> responses) {
        Set<Response> fittingResponses = new HashSet<Response>();
        for (Response response : responses) {
            String responseId = response.getID();
            if (responseIdentifiers.contains(responseId)) {
                fittingResponses.add(response);
            }
        }
        return fittingResponses;
    }

    public Multiset<Multiset<String>> getResponseMultiSet(ExpressionBean expression, Function<Response, String> answerFetcher) {
        String template = expression.getTemplate();
        List<Response> responses = expression.getResponses();

        List<String> leftRightParts = expressionToPartsDivider.divideExpressionOnEquality(template, responses, answerFetcher);
        List<String> allExpressionParts = new ArrayList<String>();
        allExpressionParts.add(template);
        allExpressionParts.addAll(leftRightParts);

        // List<Set<Response>> setsOfResponses =
        // findSetsOfResponsesByExpressionParts(allExpressionParts, responses);
        Multiset<Multiset<String>> corectResponsesSet = findSetsAnsewerExpressionParts(allExpressionParts, responses, answerFetcher);
        return corectResponsesSet;
    }

    private Multiset<Multiset<String>> findSetsAnsewerExpressionParts(List<String> allExpressionParts, List<Response> responses,
                                                                      Function<Response, String> answerFetcher) {

        Multiset<Multiset<String>> result = HashMultiset.create();

        for (String expressionPart : allExpressionParts) {
            List<String> responseIdentifiers = identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(expressionPart);
            List<String> answerValues = getCorectValues(responseIdentifiers, responses, answerFetcher);
            Multiset<String> expressionPartMultiSet = HashMultiset.create(answerValues);

            result.add(expressionPartMultiSet);
        }

        return result;
    }

    public List<String> getCorectValues(List<String> responseIdentifiers, List<Response> responses, Function<Response, String> answerFetcher) {
        List<String> result = new ArrayList<String>();

        Set<Response> foundResponses = getResponsesByIds(responseIdentifiers, responses);

        for (Response response : foundResponses) {
            result.add(answerFetcher.apply(response));
        }

        return result;
    }
}
