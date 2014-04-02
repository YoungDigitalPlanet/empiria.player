package eu.ydp.empiria.player.client.module.info;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;

import java.util.List;

public class ContentFieldInfoListProvider {

	@Inject
	private DataSourceDataSupplier dataSourceDataSupplier;
	@Inject
	private SessionDataSupplier sessionDataSupplier;
	@Inject
	private FieldValueHandlerFactory handlerFactory;
	@Inject
	private ContentFieldInfoFactory contentFieldInfoFactory;

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
		FieldValueHandler assessmentResultValueHandler = handlerFactory.getAssessmentResultValueHandler(getAssessmentVariableProvider());

		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_RESULT, assessmentResultValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getResultInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler resultValueHandler = handlerFactory.getResultValueHandler(sessionDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_RESULT, resultValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getPageCountInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler pageCountValueHandler = handlerFactory.getPageCountValueHandler(dataSourceDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_COUNT, pageCountValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getItemIndexInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler itemIndexValueHandler = handlerFactory.getItemIndexValueHandler();

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_INDEX, itemIndexValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_NUM, itemIndexValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getTitleInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler titleValueHandler = handlerFactory.getTitleValueHandler(dataSourceDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_TITLE, titleValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_TITLE, titleValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getItemInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler itemValueHandler = handlerFactory.getProviderValueHandler(sessionDataSupplier);

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
		FieldValueHandler assessmentValueHandler = handlerFactory.getProviderAssessmentValueHandler(getAssessmentVariableProvider());

		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_TODO, assessmentValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_DONE, assessmentValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_CHECKS, assessmentValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_MISTAKES, assessmentValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_SHOW_ANSWERS, assessmentValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_RESET, assessmentValueHandler));

		return contentFieldInfos;
	}

	private VariableProviderSocket getAssessmentVariableProvider() {
		return sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
	}

	private List<ContentFieldInfo> getReportFeedbackInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler feedbackValueHandler = handlerFactory.getFeedbackValueHandler(sessionDataSupplier,dataSourceDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_FEEDBACK, feedbackValueHandler));

		return contentFieldInfos;
	}
}
