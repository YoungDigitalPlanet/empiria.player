package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collection;

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
public class SurfacesOffsetsCalculatorTest {

	@InjectMocks
	private SurfacesOffsetsCalculator testObj;

	@Mock
	private ConnectionItems connectionItems;

	@Mock
	private SurfacesOffsetsExtractor offsetsExtractor;

	@Mock
	private Collection<ConnectionItem> allItems;

	@Before
	public void setUp() throws Exception {
		when(connectionItems.getAllItems()).thenReturn(allItems);

		when(offsetsExtractor.extractLeftOffsets(connectionItems)).thenReturn(Lists.newArrayList(10, 30, 20, 40));
		when(offsetsExtractor.extractTopOffsets(connectionItems)).thenReturn(Lists.newArrayList(20, 10, 40, 30));
		when(offsetsExtractor.extractBottomOffsets(connectionItems)).thenReturn(Lists.newArrayList(30, 20, 50, 40));
		when(offsetsExtractor.extractRightOffsets(connectionItems)).thenReturn(Lists.newArrayList(50, 50, 50, 50));
	}

	@Test
	public void shouldFindMaxLeftOffset() throws Exception {
		// when
		int result = testObj.findMaxOffsetLeft(connectionItems);

		// then
		assertThat(result).isEqualTo(40);
	}

	@Test
	public void shouldFindMinLeftOffset() throws Exception {
		// when
		int result = testObj.findMinOffsetLeft(connectionItems);

		// then
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void shouldFindMaxTopOffset() throws Exception {
		// when
		int result = testObj.findMaxOffsetTop(connectionItems);

		// then
		assertThat(result).isEqualTo(40);
	}

	@Test
	public void shouldFindMinTopOffset() throws Exception {
		// when
		int result = testObj.findMinOffsetTop(connectionItems);

		// then
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void shouldFindMaxOffsetRight() throws Exception {
		// when
		int result = testObj.findMaxOffsetRight(connectionItems);

		// then
		assertThat(result).isEqualTo(50);
	}

	@Test
	public void shouldFindMaxOffsetBottom() throws Exception {
		// when
		int result = testObj.findMaxOffsetBottom(connectionItems);

		// then
		assertThat(result).isEqualTo(50);
	}
}
