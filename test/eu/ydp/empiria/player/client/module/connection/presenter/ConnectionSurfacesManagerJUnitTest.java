package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.ConnectionSurfacesManagerFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.util.position.Point;

@SuppressWarnings("PMD")
public class ConnectionSurfacesManagerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static final int HEIGHT = 40;
	private static final int WIDTH = 30;

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.install(new FactoryModuleBuilder().build(ConnectionSurfacesManagerFactory.class));
		}

		@Provides
		public ConnectionSurface getSurface() {
			return mock(ConnectionSurface.class);
		}
	}

	private ConnectionSurfacesManager instance;
	private HasDimensions hasDimension;
	private ImmutableList<String> idList;

	@Before
	public void before() {
		GuiceModuleConfiguration configuration = new GuiceModuleConfiguration();
		setUpAndOverrideMainModule(configuration, new CustomGinModule());
		hasDimension = mock(HasDimensions.class);
		doReturn(HEIGHT).when(hasDimension).getHeight();
		doReturn(WIDTH).when(hasDimension).getWidth();
		instance = spy(injector.getInstance(ConnectionSurfacesManagerFactory.class).getConnectionSurfacesManager(hasDimension));
		idList = new ImmutableList.Builder<String>().add("id1").add("id2").add("id3").add("id4").build();
	}

	private List<ConnectionSurface> getConnectionSurfaces() {
		ArrayList<ConnectionSurface> surfaces = new ArrayList<ConnectionSurface>();
		for (String id : idList) {
			surfaces.add(instance.getOrCreateSurface(id));
		}
		return surfaces;
	}

	private void putConnections(List<ConnectionSurface> connectionSurfaces) {
		int index = 0;
		for (ConnectionSurface surface : connectionSurfaces) {
			instance.putSurface(new ConnectionPairEntry<String, String>(idList.get(index), idList.get(index)), surface);
			index++;
		}
	}

	@Test
	public void testGetOrCreateSurface() {
		Set<ConnectionSurface> previous = new HashSet<ConnectionSurface>();
		for (String id : idList) {
			ConnectionSurface orCreateSurface = instance.getOrCreateSurface(id);
			assertNotNull(orCreateSurface);
			assertEquals(orCreateSurface, instance.getOrCreateSurface(id));
			assertFalse(previous.contains(orCreateSurface));
			previous.add(orCreateSurface);
		}
	}

	@Test
	public void testResetAll() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		instance.resetAll();
		for (ConnectionSurface surface : connectionSurfaces) {
			verify(surface).removeFromParent();
		}
		assertTrue(instance.surfaces.size()==0);
		List<ConnectionSurface> connectionSurfaces2 = getConnectionSurfaces();
		assertFalse(connectionSurfaces.containsAll(connectionSurfaces2));
	}

	@Test
	public void testHasConnections() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		for (String id : idList) {
			assertTrue(instance.hasConnections(id));
		}

		instance.resetAll();

		int index = 0;
		for (ConnectionSurface surface : connectionSurfaces) {
			instance.putSurface(new ConnectionPairEntry<String, String>(idList.get(index) + index, idList.get(index)), surface);
			index++;
		}

		for (String id : idList) {
			assertTrue(instance.hasConnections(id));
		}

	}

	@Test
	public void testPutSurface_getOrCreateSurfaceShouldReturnNewSurface(){
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		ConnectionSurface surface = connectionSurfaces.get(0);
		instance.putSurface(new ConnectionPairEntry<String, String>(idList.get(0), "test"), surface);

		ConnectionSurface newSurface = instance.getOrCreateSurface(idList.get(0));

		assertNotSame(surface, newSurface);
	}

	@Test
	public void testHasConnectionsNoConnectins() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		for (String id : idList) {
			assertFalse(instance.hasConnections(id + "test"));
		}
	}

	@Test
	public void testClearConnectionSurfacePresent() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		for (String id : idList) {
			instance.clearConnectionSurface(new ConnectionPairEntry<String, String>(id, id));
		}

		for (ConnectionSurface surface : connectionSurfaces) {
			verify(surface).clear();
			verify(surface).removeFromParent();
		}

		for (String id : idList) {
			assertFalse(instance.containsSurface(new ConnectionPairEntry<String, String>(id, id)));
		}
	}

	@Test
	public void testClearConnectionSurfaceNotPresent() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		for (String id : idList) {
			instance.clearConnectionSurface(new ConnectionPairEntry<String, String>(id + "2", id));
		}

		for (ConnectionSurface surface : connectionSurfaces) {
			verify(surface, times(0)).clear();
			verify(surface, times(0)).removeFromParent();
		}

		for (String id : idList) {
			assertTrue(instance.containsSurface(new ConnectionPairEntry<String, String>(id, id)));
		}
	}

	@Test
	public void testFindPointOnPathNoMatch() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		ConnectionPairEntry<String, String> findPointOnPath = instance.findPointOnPath(new Point(20, 20));
		assertNull(findPointOnPath);
	}

	@Test
	public void testFindPointOnPathMatch() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);
		ConnectionSurface connectionSurface = connectionSurfaces.get(2);
		Point point = new Point(20, 20);
		doReturn(true).when(connectionSurface).isPointOnPath(Mockito.eq(point.getX()), Mockito.eq(point.getY()), Mockito.anyInt());

		// test
		ConnectionPairEntry<String, String> findPointOnPath = instance.findPointOnPath(point);
		assertNotNull(findPointOnPath);
		assertEquals(findPointOnPath.getSource(), idList.get(2));
		assertEquals(findPointOnPath.getTarget(), idList.get(2));

	}

	@Test
	public void testContainsSurfaceAllMatch() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);

		for (String id : idList) {
			assertTrue(instance.containsSurface(new ConnectionPairEntry<String, String>(id, id)));
		}
	}

	@Test
	public void testContainsSurfaceNoMatch() {
		for (String id : idList) {
			assertFalse(instance.containsSurface(new ConnectionPairEntry<String, String>(id, id)));
		}

		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);

		for (String id : idList) {
			assertFalse(instance.containsSurface(new ConnectionPairEntry<String, String>(id + id, id)));
		}
	}

	@Test
	public void testRemoveSurfaceFromParentAllRemoved() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);

		for (String id : idList) {
			instance.removeSurfaceFromParent(new ConnectionPairEntry<String, String>(id, id));
		}

		for (ConnectionSurface surface : connectionSurfaces) {
			verify(surface, times(1)).removeFromParent();
		}
	}

	@Test
	public void testRemoveSurfaceFromParentNoMatch() {
		List<ConnectionSurface> connectionSurfaces = getConnectionSurfaces();
		putConnections(connectionSurfaces);

		for (String id : idList) {
			instance.removeSurfaceFromParent(new ConnectionPairEntry<String, String>(id + id, id));
		}

		for (ConnectionSurface surface : connectionSurfaces) {
			verify(surface, times(0)).removeFromParent();
		}

	}
}
