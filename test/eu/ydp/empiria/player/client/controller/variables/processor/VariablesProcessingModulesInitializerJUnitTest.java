package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedAnswersManager;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

@RunWith(MockitoJUnitRunner.class)
public class VariablesProcessingModulesInitializerJUnitTest {

	private VariablesProcessingModulesInitializer variablesProcessingModulesInitializer;

	@Mock
	private GroupedAnswersManager groupedAnswersManager;
	@Mock
	private ModulesVariablesProcessor modulesVariablesProcessor;
	@Mock
	private MistakesInitializer mistakesInitializer;

	@Before
	public void setUp() throws Exception {
		variablesProcessingModulesInitializer = new VariablesProcessingModulesInitializer(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
	}

	@After
	public void after() {
		verifyNoMoreInteractions(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
	}

	@Test
	public void shouldCallAllRelatedInitializations() throws Exception {
		Map<String, Response> responses = Maps.newHashMap();
		Map<String, Outcome> outcomes = Maps.newHashMap();
		variablesProcessingModulesInitializer.initializeVariableProcessingModules(responses, outcomes);

		InOrder inOrder = inOrder(groupedAnswersManager, modulesVariablesProcessor, mistakesInitializer);
		inOrder.verify(groupedAnswersManager).initialize(responses);
		inOrder.verify(modulesVariablesProcessor).initialize(responses);
		inOrder.verify(mistakesInitializer).initialize(outcomes);
	}

	@Test
	public void validateAllConstructorFieldsAnnotatedWithPageScoped() throws Exception {
		Constructor<?>[] constructors = VariablesProcessingModulesInitializer.class.getConstructors();
		Constructor<?> constructor = getInjectableConstructor(constructors);

		Annotation[][] parametersAnnotations = constructor.getParameterAnnotations();
		for (Annotation[] parameterAnnotations : parametersAnnotations) {
			assertThatParameterIsAnnotatedWithPageScoped(parameterAnnotations);
		}
	}

	private void assertThatParameterIsAnnotatedWithPageScoped(Annotation[] parameterAnnotations) {
		boolean isPageScopedAnnotated = containsAnnotation(parameterAnnotations, PageScoped.class);
		String errorMessage = "Parameter is not annotated with: " + PageScoped.class.getName();
		assertEquals(errorMessage, true, isPageScopedAnnotated);
	}

	private boolean containsAnnotation(Annotation[] parameterAnnotations, Class<PageScoped> annotationClass) {
		for (Annotation annotation : parameterAnnotations) {
			if (annotation.annotationType().equals(annotationClass)) {
				return true;
			}
		}
		return false;
	}

	private Constructor<?> getInjectableConstructor(Constructor<?>[] constructors) {
		for (Constructor<?> constructor : constructors) {
			if (constructor.isAnnotationPresent(Inject.class)) {
				return constructor;
			}
		}
		throw new IllegalArgumentException("There is no constructor annotated with inject!");
	}
}
