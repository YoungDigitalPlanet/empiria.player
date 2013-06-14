package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.fest.assertions.api.Assertions.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

@RunWith(MockitoJUnitRunner.class)
public class SurfacesOffsetsUtilsJUnitTest {

	private final SurfacesOffsetsUtils surfacesOffsetsUtils = new SurfacesOffsetsUtils();
	@Mock
	private ConnectionItems connectionItems;
	private Collection<ConnectionItem> allItems;
	
	
	
	@Before
	public void setUp() throws Exception {
		allItems = Lists.newArrayList();
		
		when(connectionItems.getAllItems())
			.thenReturn(allItems);
		
		//most left
		addConnectionItem(10, 20);
		//most top
		addConnectionItem(30, 10);
		//most bottom
		addConnectionItem(20, 40);
		//most right
		addConnectionItem(40, 30);
	}

	@Test
	public void shouldFindMaxLeftOffset() throws Exception {
		int result = surfacesOffsetsUtils.findMaxOffsetLeft(connectionItems);
		assertThat(result).isEqualTo(40);
	}
	
	@Test
	public void shouldFindMinLeftOffset() throws Exception {
		int result = surfacesOffsetsUtils.findMinOffsetLeft(connectionItems);
		assertThat(result).isEqualTo(10);
	}

	@Test
	public void shouldFindMaxTopOffset() throws Exception {
		int result = surfacesOffsetsUtils.findMaxTopOffset(connectionItems);
		assertThat(result).isEqualTo(40);
	}
	
	@Test
	public void shouldFindMinTopOffset() throws Exception {
		int result = surfacesOffsetsUtils.findMinTopOffset(connectionItems);
		assertThat(result).isEqualTo(10);
	}

	private void addConnectionItem(int leftOffset, int topOffset) {
		ConnectionItem item = Mockito.mock(ConnectionItem.class);
		
		when(item.getOffsetLeft())
			.thenReturn(leftOffset);
		
		when(item.getOffsetTop())
			.thenReturn(topOffset);
		
		allItems.add(item);
	}

}
