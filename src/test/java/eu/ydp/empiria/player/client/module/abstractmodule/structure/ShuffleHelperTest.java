package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.structure.ModuleBean;

@SuppressWarnings("PMD")
public class ShuffleHelperTest {

	private static class BeanItem implements HasFixed {
		private final int index;
		private final boolean shuffle;

		public BeanItem(int index, boolean shuffle) {
			this.index = index;
			this.shuffle = shuffle;
		}

		@Override
		public boolean isFixed() {
			return shuffle;
		}

		@Override
		public String toString() {
			return "index=" + index + ", shuffle=" + shuffle;
		}

	}

	public static class Bean extends ModuleBean implements HasShuffle {
		@Override
		public boolean isShuffle() {
			return false;
		}
	}

	private final List<BeanItem> testList = new ArrayList<ShuffleHelperTest.BeanItem>();

	@Before
	public void before() {
		for (int x = 0; x < 9; ++x) {
			testList.add(new BeanItem(x, x % 2 == 0));
		}
	}

	@Test
	public void shuffleEmptyListTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(true);
		testList.clear();
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		assertTrue(testList.size() == randomizeChoices.size());
	}

	@Test
	public void sizeShuffleTrueTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(true);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		assertTrue(testList.size() == randomizeChoices.size());
	}

	@Test
	public void sizeShuffleFalseTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(false);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		assertTrue(testList.size() == randomizeChoices.size());
	}

	@Test
	public void shuffleTrueTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(true);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		assertNotSame(testList, randomizeChoices);
	}

	@Test
	public void shuffleFalseTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(false);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		assertEquals(testList, randomizeChoices);
	}

	@Test
	public void fixedWithShuffleTrueTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(true);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		for (int x = 0; x < testList.size(); ++x) {
			if (testList.get(x).isFixed()) {
				assertEquals(testList.get(x), randomizeChoices.get(x));
			}
		}
	}

	@Test
	public void fixedWithShuffleFalseTest() {
		ShuffleHelper instance = new ShuffleHelper();
		Bean bean = Mockito.mock(Bean.class);
		when(bean.isShuffle()).thenReturn(false);
		List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
		for (int x = 0; x < testList.size(); ++x) {
			if (testList.get(x).isFixed()) {
				assertEquals(testList.get(x), randomizeChoices.get(x));
			}
		}
	}

}
