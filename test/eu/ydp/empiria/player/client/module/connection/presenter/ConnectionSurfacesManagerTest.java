package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionSurfacesManagerTest {
	@InjectMocks
	private ConnectionSurfacesManager testObj;

	@Mock
	private HasDimensions hasDimension;

	@Mock
	private ConnectionModuleFactory connectionModuleFactory;

	private Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces;
	private Map<String, ConnectionSurface> surfaces;

	private ConnectionSurface cs1 = mock(ConnectionSurface.class);
	private ConnectionSurface cs2 = mock(ConnectionSurface.class);
	private ConnectionSurface cs3 = mock(ConnectionSurface.class);
	private ConnectionPairEntry<String, String> cpe1 = connectionPairEntry("s1", "t1");
	private ConnectionPairEntry<String, String> cpe2 = connectionPairEntry("s2", "t2");
	private ConnectionPairEntry<String, String> cpe3 = connectionPairEntry("s3", "t3");

	@Before
	public void before() throws Exception {
		connectedSurfaces = connectedSurfaces();
		surfaces = surfaces();

		connectedSurfaces.put(cpe1, cs1);
		connectedSurfaces.put(cpe2, cs2);
		connectedSurfaces.put(cpe3, cs3);
	}

	@Test
	public void testResetAll() {
		// when
		testObj.resetAll(connectedSurfaces);

		// then
		assertTrue(connectedSurfaces.isEmpty());

		verify(cs1).removeFromParent();
		verify(cs2).removeFromParent();
		verify(cs3).removeFromParent();

		verifyNoMoreInteractions(cs1, cs2, cs3);
	}

	@Test
	public void testHasConnections_trueForSource() {
		// when
		boolean actual = testObj.hasConnections(connectedSurfaces, "s3");

		// then
		assertTrue(actual);

		verify(cpe3).getSource();
		verify(cpe3, never()).getTarget();

		verifyNoMoreInteractions(cpe3);
	}

	@Test
	public void testHasConnections_trueForTarget() {
		// when
		boolean actual = testObj.hasConnections(connectedSurfaces, "t2");

		// then
		assertTrue(actual);

		verify(cpe2).getSource();
		verify(cpe2).getTarget();

		verifyNoMoreInteractions(cpe2);
	}

	@Test
	public void testHasConnections_false() {
		// when
		boolean actual = testObj.hasConnections(connectedSurfaces, "n5");

		// then
		assertFalse(actual);

		verify(cpe1).getSource();
		verify(cpe1).getTarget();

		verify(cpe2).getSource();
		verify(cpe2).getTarget();

		verify(cpe3).getSource();
		verify(cpe3).getTarget();

		verifyNoMoreInteractions(cpe1, cpe2, cpe3);
	}

	@Test
	public void testClearConnectionSurface_existing() {
		// when
		testObj.clearConnectionSurface(connectedSurfaces, cpe2);

		// then
		assertNull(connectedSurfaces.get(cpe2));

		verify(cs2).clear();
		verify(cs2).removeFromParent();

		verifyNoMoreInteractions(cs2);
	}

	@Test
	public void testClearConnectionSurface_nonExisting() {
		// when
		testObj.clearConnectionSurface(connectedSurfaces, connectionPairEntry("nonExistingSource", "nonExistingTarget"));

		// then
		assertEquals(3, connectedSurfaces.size());
	}

	@Test
	public void testGetOrCreateSurface_existing() {
		// given
		ConnectionSurface expected = mock(ConnectionSurface.class);
		String key = "someId";

		surfaces.put(key, expected);

		// when
		ConnectionSurface actual = testObj.getOrCreateSurface(surfaces, key, mock(HasDimensions.class));

		// then
		assertEquals(expected, actual);
	}

	@Test
	public void testGetOrCreateSurface_nonExisting() {
		// given
		surfaces.put("someId", mock(ConnectionSurface.class));
		ConnectionSurface expected = mock(ConnectionSurface.class);

		when(connectionModuleFactory.getConnectionSurface(anyInt(), anyInt())).thenReturn(expected);

		// when
		ConnectionSurface actual = testObj.getOrCreateSurface(surfaces, "otherId", hasDimension);

		// then
		assertEquals(expected, actual);
		assertEquals(2, surfaces.size());
		assertTrue(surfaces.containsKey("otherId"));

		verify(connectionModuleFactory).getConnectionSurface(anyInt(), anyInt());

		verifyNoMoreInteractions(connectionModuleFactory);
	}

	@Test
	public void testRemoveSurfaceFromItem_existing() {
		// given
		surfaces.put("someId", mock(ConnectionSurface.class));

		// when
		testObj.removeSurfaceForItem(surfaces, "someId");

		// then
		assertFalse(surfaces.containsKey("someId"));

	}

	@Test
	public void testRemoveSurfaceFromItem_nonExisting() {
		// given
		surfaces.put("someId", mock(ConnectionSurface.class));

		// when
		testObj.removeSurfaceForItem(surfaces, "otherId");

		// then
		assertTrue(surfaces.containsKey("someId"));
	}

	@Test
	public void testFindPointOnPath_existing() {
		// given
		when(cs1.isPointOnPath(anyInt(), anyInt(), anyInt())).thenReturn(Boolean.FALSE);
		when(cs2.isPointOnPath(eq(0), eq(0), anyInt())).thenReturn(Boolean.TRUE);
		when(cs3.isPointOnPath(anyInt(), anyInt(), anyInt())).thenReturn(Boolean.FALSE);

		// when
		ConnectionPairEntry<String, String> actual = testObj.findPointOnPath(connectedSurfaces, new Point(0, 0));

		// then
		assertEquals(cpe2.getSource(), actual.getSource());
		assertEquals(cpe2.getTarget(), actual.getTarget());

		verify(cs2).isPointOnPath(anyInt(), anyInt(), anyInt());
		verifyNoMoreInteractions(cs2);
	}

	@Test
	public void testFindPointOnPath_nonExisting() {
		// when
		ConnectionPairEntry<String, String> actual = testObj.findPointOnPath(connectedSurfaces, new Point(1000, 1000));

		// then
		assertNull(actual);
	}

	@Test
	public void testPutSurface_existing() {
		// given
		ConnectionSurface cs = mock(ConnectionSurface.class);
		surfaces.put("any source", cs);
		ConnectionPairEntry<String, String> cpe = connectionPairEntry("any source", "any");

		// when
		testObj.putSurface(surfaces, connectedSurfaces, cpe, cs);

		// then
		assertTrue(connectedSurfaces.containsKey(cpe));
		assertEquals(cs, connectedSurfaces.get(cpe));

		assertFalse(surfaces.containsKey(cpe.getSource()));
	}

	@Test
	public void testPutSurface_nonExisting() {
		// given
		ConnectionSurface cs = mock(ConnectionSurface.class);
		ConnectionPairEntry<String, String> cpe = connectionPairEntry("any source", "any");

		// when
		testObj.putSurface(surfaces, connectedSurfaces, cpe, cs);

		// then
		assertTrue(connectedSurfaces.containsKey(cpe));
		assertEquals(cs, connectedSurfaces.get(cpe));

		assertFalse(surfaces.containsKey(cpe.getSource()));
	}

	@Test
	public void testContainsSurface_exisitng() {
		// when
		boolean actual = testObj.containsSurface(connectedSurfaces, cpe3);

		// then
		assertTrue(actual);
	}

	@Test
	public void testContainsSurface_nonExisitng() {
		// given
		ConnectionPairEntry<String, String> cpe4 = connectionPairEntry("s4", "t4");

		// when
		boolean actual = testObj.containsSurface(connectedSurfaces, cpe4);

		// then
		assertFalse(actual);
	}

	@Test
	public void testRemoveFromParent_existing() {
		// when
		testObj.removeSurfaceFromParent(connectedSurfaces, cpe2);

		// then
		verify(cs2).removeFromParent();
	}

	@Test
	public void testRemoveFromParent_nonExisting() {
		// when
		testObj.removeSurfaceFromParent(connectedSurfaces, new ConnectionPairEntry<String, String>("abcd", "dcbe"));

		// then
		verify(cs1, never()).removeFromParent();
		verify(cs2, never()).removeFromParent();
		verify(cs3, never()).removeFromParent();
	}

	private Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces() {
		return Maps.newHashMap();
	}

	private Map<String, ConnectionSurface> surfaces() {
		return Maps.newHashMap();
	}

	@SuppressWarnings("unchecked")
	private ConnectionPairEntry<String, String> connectionPairEntry(String source, String target) {
		ConnectionPairEntry<String, String> cpe = (ConnectionPairEntry<String, String>) mock(ConnectionPairEntry.class);

		when(cpe.getSource()).thenReturn(source);
		when(cpe.getTarget()).thenReturn(target);

		return cpe;
	}
}
