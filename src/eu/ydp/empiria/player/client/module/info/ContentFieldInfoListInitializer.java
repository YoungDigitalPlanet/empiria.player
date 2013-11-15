package eu.ydp.empiria.player.client.module.info;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;

public class ContentFieldInfoListInitializer {

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

	public List<ContentFieldInfo> initilize() {
		List<ContentFieldInfo> fieldInfos = Lists.newArrayList();
		FieldValueHandler itemValueHandler = handlerFactory.getProviderValueHandler(sessionDataSupplier);
		FieldValueHandler assessmentValueHandler = handlerFactory.getProviderAssessmentValueHandler(getAssessmentVariableProvider());
		FieldValueHandler titleValueHandler = handlerFactory.getTitleValueHandler(dataSourceDataSupplier);
		FieldValueHandler itemIndexValueHandler = handlerFactory.getItemIndexValueHandler();
		FieldValueHandler pageCountValueHandler = handlerFactory.getPageCountValueHandler(dataSourceDataSupplier);
		FieldValueHandler assessmentResultValueHandler = handlerFactory.getAssessmentResultValueHandler(getAssessmentVariableProvider());
		FieldValueHandler resultValueHandler = handlerFactory.getResultValueHandler(sessionDataSupplier);

		fieldInfos.add(contentFieldInfoFactory.create(ITEM_TODO, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_DONE, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_CHECKS, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_MISTAKES, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_SHOW_ANSWERS, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_RESET, itemValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_TITLE, titleValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_INDEX, itemIndexValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_NUM, itemIndexValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_COUNT, pageCountValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(ITEM_RESULT, resultValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_TODO, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_DONE, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_CHECKS, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_MISTAKES, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_SHOW_ANSWERS, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_RESET, assessmentValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_TITLE, titleValueHandler));
		fieldInfos.add(contentFieldInfoFactory.create(TEST_RESULT, assessmentResultValueHandler));

		return fieldInfos;
	}

	private VariableProviderSocket getAssessmentVariableProvider() {
		return sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
	}
}
