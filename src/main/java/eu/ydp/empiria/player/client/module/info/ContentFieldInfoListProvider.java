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
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.module.info.handler.*;

import java.util.List;

public class ContentFieldInfoListProvider {

    @Inject
    private ContentFieldInfoFactory contentFieldInfoFactory;
    @Inject
    private ProviderAssessmentValueHandler providerAssessmentValueHandler;
    @Inject
    private AssessmentResultValueHandler assessmentResultValueHandler;
    @Inject
    private ResultValueHandler resultValueHandler;
    @Inject
    private PageCountValueHandler pageCountValueHandler;
    @Inject
    private ItemIndexValueHandler itemIndexValueHandler;
    @Inject
    private TitleValueHandler titleValueHandler;
    @Inject
    private ItemValueHandler itemValueHandler;
    @Inject
    private FeedbackValueHandler feedbackValueHandler;

    private static final String TEST_RESULT = "test.result";
    private static final String TEST_TITLE = "test.title";
    private static final String TEST_RESET = "test.reset";
    private static final String TEST_SHOW_ANSWERS = "test.show_answers";
    private static final String TEST_MISTAKES = "test.mistakes";
    private static final String TEST_CHECKS = "test.checks";
    private static final String TEST_DONE = "test.done";
    private static final String TEST_TODO = "test.todo";
    private static final String ITEM_RESULT = "item.result";
    private static final String ITEM_PAGE_COUNT = "item.page_count";
    private static final String ITEM_PAGE_NUM = "item.page_num";
    private static final String ITEM_INDEX = "item.index";
    private static final String ITEM_TITLE = "item.title";
    private static final String ITEM_RESET = "item.reset";
    private static final String ITEM_SHOW_ANSWERS = "item.show_answers";
    private static final String ITEM_MISTAKES = "item.mistakes";
    private static final String ITEM_CHECKS = "item.checks";
    private static final String ITEM_DONE = "item.done";
    private static final String ITEM_TODO = "item.todo";
    private static final String ITEM_FEEDBACK = "item.feedback";

    public List<ContentFieldInfo> get() {
        List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

        List<ContentFieldInfo> itemInfos = getItemInfos();
        List<ContentFieldInfo> assessmentInfos = getAssessmentInfos();
        List<ContentFieldInfo> titleInfos = getTitleInfos();
        List<ContentFieldInfo> itemIndexInfos = getItemIndexInfos();
        List<ContentFieldInfo> pageCountInfos = getPageCountInfos();
        List<ContentFieldInfo> resultInfos = getResultInfos();
        List<ContentFieldInfo> assessmentResultInfos = getAssessmentResultInfos();
        List<ContentFieldInfo> reportFeedbackInfos = getReportFeedbackInfos();

        fieldInfos.addAll(itemInfos);
        fieldInfos.addAll(assessmentInfos);
        fieldInfos.addAll(titleInfos);
        fieldInfos.addAll(itemIndexInfos);
        fieldInfos.addAll(pageCountInfos);
        fieldInfos.addAll(resultInfos);
        fieldInfos.addAll(assessmentResultInfos);
        fieldInfos.addAll(reportFeedbackInfos);

        return fieldInfos;
    }

    private List<ContentFieldInfo> getAssessmentResultInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_RESULT, assessmentResultValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getResultInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_RESULT, resultValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getPageCountInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_COUNT, pageCountValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getItemIndexInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_INDEX, itemIndexValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_NUM, itemIndexValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getTitleInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_TITLE, titleValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_TITLE, titleValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getItemInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_TODO, itemValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_DONE, itemValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_CHECKS, itemValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_MISTAKES, itemValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_SHOW_ANSWERS, itemValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_RESET, itemValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getAssessmentInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_TODO, providerAssessmentValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_DONE, providerAssessmentValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_CHECKS, providerAssessmentValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_MISTAKES, providerAssessmentValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_SHOW_ANSWERS, providerAssessmentValueHandler));
        contentFieldInfos.add(contentFieldInfoFactory.create(TEST_RESET, providerAssessmentValueHandler));

        return contentFieldInfos;
    }

    private List<ContentFieldInfo> getReportFeedbackInfos() {
        List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();

        contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_FEEDBACK, feedbackValueHandler));

        return contentFieldInfos;
    }
}
