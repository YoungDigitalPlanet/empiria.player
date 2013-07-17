package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

@RunWith(MockitoJUnitRunner.class)
public class SurfacePositionFinderTest  {

	private SurfacePositionFinder finder;
	@Mock
	private SurfacesOffsetsUtils surfacesOffsetsUtils;

	@Before
	public void setUp(){
		finder = new SurfacePositionFinder(surfacesOffsetsUtils);
	}
	
	@Test
	public void shouldReturnMinLeftOffsetOfAllItems() throws Exception {
		//given
		ConnectionItems items = Mockito.mock(ConnectionItems.class);
		
		int minLeftOffset = 123;
		when(surfacesOffsetsUtils.findMinOffsetLeft(items))
			.thenReturn(minLeftOffset);
		
		//when
		int result = finder.findOffsetLeft(items);
		
		//then
		assertThat(result).isEqualTo(minLeftOffset);
	}
	
	@Test
	public void shouldReturnMinTopOffsetOfAllItems() throws Exception {
		//given
		ConnectionItems items = Mockito.mock(ConnectionItems.class);
		
		int minTopOffset = 1232342;
		when(surfacesOffsetsUtils.findMinTopOffset(items))
		.thenReturn(minTopOffset);
		
		//when
		int result = finder.findTopOffset(items);
		
		//then
		assertThat(result).isEqualTo(minTopOffset);
	}
}
