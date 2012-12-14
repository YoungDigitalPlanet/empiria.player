package eu.ydp.empiria.player.client.module.info;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;

public class ContentFieldRegistry {

	private DataSourceDataSupplier dataSourceDataSupplier;
	
	private SessionDataSupplier sessionDataSupplier;

	private FieldValueHandlerFactory handlerFactory;
	
	private final List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

	@Inject
	public ContentFieldRegistry(@Assisted DataSourceDataSupplier dataSourceDataSupplier, 
								@Assisted SessionDataSupplier sessionDataSupplier, 
								FieldValueHandlerFactory handlerFactory){
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;
		this.handlerFactory = handlerFactory;	
	}
	
	public void register(int refItemIndex){
		FieldValueHandler itemValueHandler = handlerFactory.getProviderValueHandler(getItemVariableProvider(refItemIndex));
		FieldValueHandler assessmentValueHandler = handlerFactory.getProviderValueHandler(getAssessmentVariableProvider());
		FieldValueHandler titleValueHandler = handlerFactory.getTitleValueHandler(dataSourceDataSupplier);
		FieldValueHandler itemIndexValueHandler = handlerFactory.getItemIndexValueHandler();
		FieldValueHandler pageCountValueHandler = handlerFactory.getPageCountValueHandler(dataSourceDataSupplier);
		FieldValueHandler testResultValueHandler = handlerFactory.getResultValueHandler(getAssessmentVariableProvider());
		FieldValueHandler itemResultValueHandler = handlerFactory.getResultValueHandler(getItemVariableProvider(refItemIndex));
		
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
		
		Iterator<Entry<String, FieldValueHandler>> iterator = registry.entrySet().iterator();
		
		while(iterator.hasNext()){
			Entry<String, FieldValueHandler> registryEntry = iterator.next();
			fieldInfos.add(createFieldInfo(registryEntry.getKey()).setHandler(registryEntry.getValue()));
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
		return Lists.newArrayList(fieldInfos);
	}	
	
}
