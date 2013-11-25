package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static eu.ydp.empiria.player.client.module.connection.ConnectionItemFluentMockBuilder.*;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

@RunWith(MockitoJUnitRunner.class)
public class SurfacesOffsetsExtractorTest {

	@InjectMocks
	private SurfacesOffsetsExtractor testObj;

	@Mock
	private ConnectionItems connectionItems;

	@Before
	public void setUp() {
		final ConnectionItem ci1 = newConnectionItem().withOffsets(10, 20).withDimensions(50, 100).build();
		final ConnectionItem ci2 = newConnectionItem().withOffsets(20, 30).withDimensions(50, 100).build();
		final ConnectionItem ci3 = newConnectionItem().withOffsets(30, 40).withDimensions(50, 100).build();
		final ConnectionItem ci4 = newConnectionItem().withOffsets(40, 50).withDimensions(50, 100).build();
		List<ConnectionItem> list = Lists.newArrayList(ci1, ci2, ci3, ci4);

		when(connectionItems.getAllItems()).thenReturn(list);
	}

	@Test
	public void shouldGetLeftOffsetsOnly() {
		// when
		Collection<Integer> result = testObj.extractLeftOffsets(connectionItems);

		// then
		assertThat(result).containsOnly(10, 20, 30, 40);
	}

	@Test
	public void shouldGetTopOffsetsOnly() {
		// when
		Collection<Integer> result = testObj.extractTopOffsets(connectionItems);

		// then
		assertThat(result).containsOnly(20, 30, 40, 50);
	}
	
	@Test
	public void shouldGetRightOffsetsOnly() {
		// when
		Collection<Integer> result = testObj.extractRightOffsets(connectionItems);
		
		// then
		assertThat(result).containsOnly(60, 70, 80, 90);
	}
	
	@Test
	public void shouldGetBottomOffsetsOnly() {
		// when
		Collection<Integer> result = testObj.extractBottomOffsets(connectionItems);
		
		// then
		assertThat(result).containsOnly(120, 130, 140, 150);
		
	}

}
