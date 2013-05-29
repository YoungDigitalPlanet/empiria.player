package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScopeStack;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class ResponseModuleScopedProvider implements Provider<Response>{

	@Inject
	private ModuleScopeStack moduleScopeStack;
	
	@PageScoped 
	@Inject
	private ItemResponseManager responseManager;
	
	@Override
	public Response get() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		Element element = context.getXmlElement();
		String responseId = findResponseIdByXml(element);
		
		Map<String, Response> responses = responseManager.getVariablesMap();
		Response response = responses.get(responseId);
		return response;
	}

	private String findResponseIdByXml(Element element) {
		String responseId = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		return responseId;
	}

}
