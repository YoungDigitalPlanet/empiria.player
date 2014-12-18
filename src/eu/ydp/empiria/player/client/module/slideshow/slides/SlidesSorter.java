package eu.ydp.empiria.player.client.module.slideshow.slides;

import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import java.util.*;

public class SlidesSorter {

	private final Comparator<SlideBean> comparator = new Comparator<SlideBean>() {

		@Override
		public int compare(SlideBean s1, SlideBean s2) {
			return s1.getStartTime() - s2.getStartTime();
		}
	};

	public void sortByTime(List<SlideBean> list) {
		Collections.sort(list, comparator);
	}
}
