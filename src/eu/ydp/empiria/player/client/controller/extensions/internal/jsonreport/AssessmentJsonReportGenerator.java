package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportProvider;
import eu.ydp.empiria.player.client.controller.report.HintInfo;
import eu.ydp.empiria.player.client.controller.report.ItemReportProvider;
import eu.ydp.empiria.player.client.controller.report.ResultInfo;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

public class AssessmentJsonReportGenerator {

	AssessmentReportFactory factory;

	DataSourceDataSupplier dataSupplier;

	SessionDataSupplier sessionSupplier;

	@Inject
	public AssessmentJsonReportGenerator(@Assisted DataSourceDataSupplier dataSupplier, @Assisted SessionDataSupplier sessionSupplier,
			AssessmentReportFactory factory) {
		this.dataSupplier = dataSupplier;
		this.sessionSupplier = sessionSupplier;
		this.factory = factory;
	}

	public AssessmentJsonReport generate() {
		AssessmentJsonReport jsonReport = AssessmentJsonReport.create();
		AssessmentReportProvider reportProvider = factory.getAssessmentReportProvider(dataSupplier, sessionSupplier);

		appendAssessmentAttributes(jsonReport, reportProvider);
		appendItemsReports(jsonReport, reportProvider);

		return jsonReport;
	}

	private void appendAssessmentAttributes(AssessmentJsonReport jsonReport, AssessmentReportProvider reportProvider) {
		ResultInfo assessmentResultInfo = reportProvider.getResult();
		ResultJsonReport assessmentResult = getResultReport(assessmentResultInfo);
		HintInfo assessmentHintInfo = reportProvider.getHints();
		HintJsonReport assessmentHint = getHintsReport(assessmentHintInfo);

		jsonReport.setTitle(reportProvider.getTitle());
		jsonReport.setResult(assessmentResult);
		jsonReport.setHints(assessmentHint);
	}

	private void appendItemsReports(AssessmentJsonReport jsonReport, AssessmentReportProvider reportProvider) {
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
