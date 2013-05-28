package eu.ydp.empiria.player.client.gin.scopes.module;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.xml.client.Element;

import static org.junit.Assert.*;

import static org.fest.assertions.api.Assertions.assertThat;


public class ModuleScopeStackJUnitTest {

	private ModuleScopeStack scopeStack;
	
	@Before
	public void setUp() throws Exception {
		scopeStack = new ModuleScopeStack();
	}
	
	@Test (expected = EmptyStackException.class)
	public void shouldThrowExceptionWhenPopOnEmpty() throws Exception {
		scopeStack.pop();
	}

	@Test
	public void shouldReturnPushedContextWhenPop() throws Exception {
		ModuleCreationContext pushedContext = getContext();
		scopeStack.pushContext(pushedContext);
		
		ModuleCreationContext returnedContext = scopeStack.pop();
		assertThat(returnedContext).isEqualTo(pushedContext);
	}
	
	@Test
	public void shouldAlwaysReturnTopElement() throws Exception {
		ModuleCreationContext firstContext = getContext();
		scopeStack.pushContext(firstContext);
		
		assertThat(scopeStack.getCurrentTopContext()).isEqualTo(firstContext);
		
		ModuleCreationContext secondContext = getContext();
		scopeStack.pushContext(secondContext);
		
		assertThat(scopeStack.getCurrentTopContext()).isEqualTo(secondContext);
		
		scopeStack.pop();
		assertThat(scopeStack.getCurrentTopContext()).isEqualTo(firstContext);
	}

	@Test (expected = EmptyStackException.class)
	public void shouldThrowExceptionWhenAskedForTopWithEmptyStack() throws Exception {
		scopeStack.getCurrentTopContext();
	}
	
	private ModuleCreationContext getContext() {
		return new ModuleCreationContext(Mockito.mock(Element.class));
	}
}
