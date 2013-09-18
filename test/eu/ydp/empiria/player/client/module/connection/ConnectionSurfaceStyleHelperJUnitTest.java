package eu.ydp.empiria.player.client.module.connection;

import static org.junit.Assert.assertEquals;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;

@RunWith(JUnitParamsRunner.class)
public class ConnectionSurfaceStyleHelperJUnitTest {

	private ConnectionSurfaceStyleHelper helper;

	@Before
	public void setUp() {
		helper = new ConnectionSurfaceStyleHelper();
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters({
		"-1, 0", 
		"-1, -1", 
		"0, -1" 
	})
	public void shouldThrowException(int leftIndex, int rightIndex) {
		// given
		MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

		// when
		List<String> styles = helper.getStylesForSurface(type, leftIndex, rightIndex);
	}

	@Test
	@Parameters({
		"0, 0, qp-connection-line-0-0", 
		"1, 2, qp-connection-line-1-2"
	})
	public void shouldGetStyleForNormal(int leftIndex, int rightIndex, String expectedNormalStyle) {
		// given
		MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

		// when
		List<String> styles = helper.getStylesForSurface(type, leftIndex, rightIndex);

		// then
		assertEquals(styles.size(), 1);
		assertEquals(styles.get(0), expectedNormalStyle);
	}

	@Test
	@Parameters({
		"0, 0, qp-connection-line-0-0, qp-connection-line-correct-0-0", 
		"1, 2, qp-connection-line-1-2, qp-connection-line-correct-1-2"
	})
	public void shouldGetStylesForCorrect(int leftIndex, int rightIndex, String expectedNormalStyle, 
			String expectedCorrectStyle) {
		// given
		MultiplePairModuleConnectType type = MultiplePairModuleConnectType.CORRECT;

		// when
		List<String> styles = helper.getStylesForSurface(type, leftIndex, rightIndex);

		// then
		assertEquals(styles.size(), 2);
		assertEquals(styles.get(0), expectedNormalStyle);
		assertEquals(styles.get(1), expectedCorrectStyle);
	}

	@Test
	@Parameters({
		"0, 0, qp-connection-line-0-0, qp-connection-line-wrong-0-0", 
		"1, 2, qp-connection-line-1-2, qp-connection-line-wrong-1-2"
	})
	public void shouldGetStylesForWrong(int leftIndex, int rightIndex, String expectedNormalStyle, 
			String expectedWrongStyle) {
		// given
		MultiplePairModuleConnectType type = MultiplePairModuleConnectType.WRONG;

		// when
		List<String> styles = helper.getStylesForSurface(type, leftIndex, rightIndex);

		// then
		assertEquals(styles.size(), 2);
		assertEquals(styles.get(0), expectedNormalStyle);
		assertEquals(styles.get(1), expectedWrongStyle);
	}

}
