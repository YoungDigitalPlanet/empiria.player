package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class TutorConfigModuleScopedProvider implements Provider<TutorConfig> {

	private static final String TUTOR_ID_ATTR = "tutorId";

	@Inject
	private ModuleScopeStack moduleScopeStack;

	@Inject
	private TutorService tutorService;

	@Override
	public TutorConfig get() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		String tutorId = context.getXmlElement().getAttribute(TUTOR_ID_ATTR);
		return tutorService.getTutorConfig(tutorId);
	}

}
