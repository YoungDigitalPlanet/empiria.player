package eu.ydp.empiria.player.client.controller.body;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ParenthoodSocket;
import eu.ydp.gwtutil.client.collections.StackMap;

@RunWith(MockitoJUnitRunner.class)
public class ParenthoodManagerTest {
	@Spy private final StackMap<HasChildren, List<IModule>> parenthood = new StackMap<HasChildren, List<IModule>>();

	@Mock private ParenthoodSocket upperLevelParenthoodSocket;
	@InjectMocks private ParenthoodManager instance;
	@Mock private IModule module;
	@Mock private HasChildren hasChildren;

	@Test
	public void getParentNullResult() throws Exception {
		HasChildren parent = instance.getParent(module);
		assertThat(parent).isNull();
	}

	@Test
	public void getParentFromParentHood() throws Exception {
		parenthood.put(hasChildren, Lists.newArrayList(module));
		HasChildren parent = instance.getParent(module);
		assertThat(parent).isEqualTo(hasChildren);
		verifyZeroInteractions(upperLevelParenthoodSocket);
	}
	@Test
	public void getParentFromUpperLevelParenthoodSocket() throws Exception {
		doReturn(hasChildren).when(upperLevelParenthoodSocket).getParent(eq(module));
		HasChildren parent = instance.getParent(module);
		assertThat(parent).isEqualTo(hasChildren);
		verify(upperLevelParenthoodSocket).getParent(eq(module));
	}
	@Test
	public void getParentFromCache() throws Exception {
		doReturn(hasChildren).when(upperLevelParenthoodSocket).getParent(eq(module));
		HasChildren parent = instance.getParent(module);
		//second from cache
		parent = instance.getParent(module);
		assertThat(parent).isEqualTo(hasChildren);
		verify(upperLevelParenthoodSocket).getParent(eq(module));

	}

}
