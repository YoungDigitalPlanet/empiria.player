package eu.ydp.empiria.player.client.module.info;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;

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

	public List<ContentFieldInfo> get() {
		List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

		List<ContentFieldInfo> itemValueContentFieldInfos = getItemValueContentFieldInfos();
		List<ContentFieldInfo> assessmentValueContentFieldInfos = getAssessmentValueContentFieldInfos();
		List<ContentFieldInfo> titleValueContentFieldInfos = getTitleValueContentFieldInfos();
		List<ContentFieldInfo> itemIndexValueContentFieldInfos = getItemIndexValueContentFieldInfos();
		List<ContentFieldInfo> pageCountValueContentFieldInfos = getPageCountValueContentFieldInfos();
		List<ContentFieldInfo> resultValueContentFieldInfos = getResultValueContentFieldInfos();
		List<ContentFieldInfo> assessmentResultValueContentFieldInfos = getAssessmentResultValueContentFieldInfos();

		fieldInfos.addAll(itemValueContentFieldInfos);
		fieldInfos.addAll(assessmentValueContentFieldInfos);
		fieldInfos.addAll(titleValueContentFieldInfos);
		fieldInfos.addAll(itemIndexValueContentFieldInfos);
		fieldInfos.addAll(pageCountValueContentFieldInfos);
		fieldInfos.addAll(resultValueContentFieldInfos);
		fieldInfos.addAll(assessmentResultValueContentFieldInfos);

		return fieldInfos;
	}

	private List<ContentFieldInfo> getAssessmentResultValueContentFieldInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler assessmentResultValueHandler = handlerFactory.getAssessmentResultValueHandler(getAssessmentVariableProvider());

		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_RESULT, assessmentResultValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getResultValueContentFieldInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler resultValueHandler = handlerFactory.getResultValueHandler(sessionDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_RESULT, resultValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getPageCountValueContentFieldInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler pageCountValueHandler = handlerFactory.getPageCountValueHandler(dataSourceDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_COUNT, pageCountValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getItemIndexValueContentFieldInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler itemIndexValueHandler = handlerFactory.getItemIndexValueHandler();

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_INDEX, itemIndexValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_PAGE_NUM, itemIndexValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getTitleValueContentFieldInfos() {
		List<ContentFieldInfo> contentFieldInfos = Lists.newArrayList();
		FieldValueHandler titleValueHandler = handlerFactory.getTitleValueHandler(dataSourceDataSupplier);

		contentFieldInfos.add(contentFieldInfoFactory.create(ITEM_TITLE, titleValueHandler));
		contentFieldInfos.add(contentFieldInfoFactory.create(TEST_TITLE, titleValueHandler));

		return contentFieldInfos;
	}

	private List<ContentFieldInfo> getItemValueContentFieldInfos() {
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

	private List<ContentFieldInfo> getAssessmentValueContentFieldInfos() {
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
}
