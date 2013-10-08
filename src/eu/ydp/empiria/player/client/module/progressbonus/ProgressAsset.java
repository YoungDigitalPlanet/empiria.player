package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.collect.Range;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.collections.SimpleRangeMap;

public class ProgressAsset {

	final private SimpleRangeMap<Integer, ShowImageDTO> images = SimpleRangeMap.<Integer, ShowImageDTO> create();
	final private int id;

	public ProgressAsset(int id) {
		this.id = id;
	}

	public ShowImageDTO getImageForProgress(int progress) {
		return images.get(progress);
	}

	public void add(Range<Integer> range, ShowImageDTO image) {
		images.put(range, image);
	}

	public int getId() {
		return id;
	}
}
