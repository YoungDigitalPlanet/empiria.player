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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.report.*;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

import java.util.List;

public class AssessmentJsonReportGenerator {

    AssessmentReportProvider reportProvider;

    DataSourceDataSupplier dataSupplier;

    SessionDataSupplier sessionSupplier;

    @Inject
    public AssessmentJsonReportGenerator(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier, AssessmentReportProvider reportProvider) {
        this.dataSupplier = dataSupplier;
        this.sessionSupplier = sessionSupplier;
        this.reportProvider = reportProvider;
    }

    public AssessmentJsonReport generate() {
        AssessmentJsonReport jsonReport = AssessmentJsonReport.create();

        appendAssessmentAttributes(jsonReport);
        appendItemsReports(jsonReport);

        return jsonReport;
    }

    private void appendAssessmentAttributes(AssessmentJsonReport jsonReport) {
        ResultInfo assessmentResultInfo = reportProvider.getResult();
        ResultJsonReport assessmentResult = getResultReport(assessmentResultInfo);
        HintInfo assessmentHintInfo = reportProvider.getHints();
        HintJsonReport assessmentHint = getHintsReport(assessmentHintInfo);

        jsonReport.setTitle(reportProvider.getTitle());
        jsonReport.setResult(assessmentResult);
        jsonReport.setHints(assessmentHint);
    }

    private void appendItemsReports(AssessmentJsonReport jsonReport) {
        List<ItemJsonReport> itemReportList = Lists.newArrayList();

        for (ItemReportProvider itemProvider : reportProvider.getItems()) {
            ItemJsonReport itemReport = createItemReport(itemProvider);
            itemReportList.add(itemReport);
        }

        jsonReport.setItems(itemReportList);
    }

    private ItemJsonReport createItemReport(ItemReportProvider reportProvider) {
        ItemJsonReport itemReport = ItemJsonReport.create();
        HintInfo itemHintInfo = reportProvider.getHints();
        HintJsonReport itemHint = getHintsReport(itemHintInfo);
        ResultInfo itemResultInfo = reportProvider.getResult();
        ResultJsonReport itemResult = getResultReport(itemResultInfo);

        itemReport.setTitle(reportProvider.getTitle());
        itemReport.setIndex(reportProvider.getIndex());
        itemReport.setHints(itemHint);
        itemReport.setResult(itemResult);

        return itemReport;
    }

    private HintJsonReport getHintsReport(HintInfo hintInfo) {
        HintJsonReport hintReport = HintJsonReport.create();

        hintReport.setChecks(hintInfo.getChecks());
        hintReport.setMistakes(hintInfo.getMistakes());
        hintReport.setReset(hintInfo.getReset());
        hintReport.setShowAnswers(hintInfo.getShowAnswers());

        return hintReport;
    }

    private ResultJsonReport getResultReport(ResultInfo resultInfo) {
        ResultJsonReport resultReport = ResultJsonReport.create();

        resultReport.setDone(resultInfo.getDone());
        resultReport.setTodo(resultInfo.getTodo());
        resultReport.setErrors(resultInfo.getErrors());
        resultReport.setResult(resultInfo.getResult());

        return resultReport;
    }

}
