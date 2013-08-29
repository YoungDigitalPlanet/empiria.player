package eu.ydp.empiria.player.client.module.info;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;

public class ContentFieldRegistry {

	@Inject private DataSourceDataSupplier dataSourceDataSupplier;
	@Inject private SessionDataSupplier sessionDataSupplier;
	@Inject private FieldValueHandlerFactory handlerFactory;

	private final List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

	public void register(){
		FieldValueHandler itemValueHandler = handlerFactory.getProviderValueHandler(sessionDataSupplier);
		FieldValueHandler assessmentValueHandler = handlerFactory.getProviderAssessmentValueHandler(getAssessmentVariableProvider());
		FieldValueHandler titleValueHandler = handlerFactory.getTitleValueHandler(dataSourceDataSupplier);
		FieldValueHandler itemIndexValueHandler = handlerFactory.getItemIndexValueHandler();
		FieldValueHandler pageCountValueHandler = handlerFactory.getPageCountValueHandler(dataSourceDataSupplier);
		FieldValueHandler testResultValueHandler = handlerFactory.getAssessmentResultValueHandler(getAssessmentVariableProvider());
		FieldValueHandler itemResultValueHandler = handlerFactory.getResultValueHandler(sessionDataSupplier);

		Map<String, FieldValueHandler> registry = ImmutableMap.<String, FieldValueHandler>builder().
															put("item.todo", itemValueHandler).
															put("item.done", itemValueHandler).
															put("item.checks", itemValueHandler).
															put("item.mistakes", itemValueHandler).
															put("item.show_answers", itemValueHandler).
															put("item.reset", itemValueHandler).
															put("item.title", titleValueHandler).
															put("item.index", itemIndexValueHandler).
															put("item.page_num", itemIndexValueHandler).
															put("item.page_count", pageCountValueHandler).
															put("item.result", itemResultValueHandler).
															put("test.todo", assessmentValueHandler).
															put("test.done", assessmentValueHandler).
															put("test.checks", assessmentValueHandler).
															put("test.mistakes", assessmentValueHandler).
															put("test.show_answers", assessmentValueHandler).
															put("test.reset", assessmentValueHandler).
															put("test.title", titleValueHandler).
															put("test.result", testResultValueHandler).
														build();
		createFields(registry);
	}

	private void createFields(Map<String, FieldValueHandler> registry){
		Iterator<Entry<String, FieldValueHandler>> iterator = registry.entrySet().iterator();

		while(iterator.hasNext()){
			Entry<String, FieldValueHandler> registryEntry = iterator.next();
			String tagName = registryEntry.getKey();
			FieldValueHandler handler = registryEntry.getValue();
			ContentFieldInfo fieldInfo = createFieldInfo(tagName).setHandler(handler);

			fieldInfos.add(fieldInfo);
		}
	}

	protected VariableProviderSocket getAssessmentVariableProvider() {
		return sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
	}

	protected VariableProviderSocket getItemVariableProvider(int refItemIndex) {
		return sessionDataSupplier.getItemSessionDataSocket(refItemIndex).getVariableProviderSocket();
	}

	private ContentFieldInfo createFieldInfo(String tagName){
		return new ContentFieldInfo().setTagName(tagName);
	}

	public List<ContentFieldInfo> getFieldInfos() {
		registerIfRequired();
		return Lists.newArrayList(fieldInfos);
	}

	private boolean isRegistered(){
		return !fieldInfos.isEmpty();
	}

	public Optional<ContentFieldInfo> getFieldInfo(final String fieldName) {
		registerIfRequired();
		ContentFieldInfo fieldInfo = findFiledInfoByName(fieldName);
		if (fieldInfo == null) {
			return Optional.absent();
		}
		return Optional.of(fieldInfo);
	}

	private ContentFieldInfo findFiledInfoByName(final String fieldName) {
		return Iterables.find(fieldInfos, new Predicate<ContentFieldInfo>() {
			@Override
			public boolean apply(ContentFieldInfo fieldInfo) {
				return fieldInfo.getTag().equals(fieldName);
			}
		}, null);
	}

	private void registerIfRequired() {
		if (!isRegistered()) {
			register();
		}
	}

}
