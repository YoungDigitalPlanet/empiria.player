package eu.ydp.empiria.player.client.module.info;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

public class VariableInterpreter {
	
	private ContentFieldRegistry fieldRegistry;
	
	@Inject
	public VariableInterpreter(@Assisted DataSourceDataSupplier dataSourceDataSupplier, 
								@Assisted SessionDataSupplier sessionDataSupplier, 
								VariableInterpreterFactory factory) {
		this.fieldRegistry = factory.getRegistry(dataSourceDataSupplier, sessionDataSupplier);
	}
	
	public String replaceAllTags(String content, int refItemIndex) {
		fieldRegistry.register(refItemIndex);
		return replaceItemTags(content, refItemIndex);
	}
	
	private String replaceItemTags(String contentWithTags, int refItemIndex){
		String content = contentWithTags;
		
		for(ContentFieldInfo info: fieldRegistry.getFieldInfos()){
			content = replaceTag(info, content, refItemIndex);
		}
		
		return content;
	}
	
	private String replaceTag(ContentFieldInfo info, String contentWithTags, int refItemIndex){
		String content = contentWithTags;
		
		if (content.contains(info.getTag())) {
			content = content.replaceAll(info.getPattern(), info.getValue(refItemIndex));
		}
		
		return content;
	}
}
