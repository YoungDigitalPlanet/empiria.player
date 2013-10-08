package eu.ydp.empiria.player.client.module.progressbonus;

import java.util.List;

import com.google.common.collect.Range;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.collections.SimpleRangeMap;
import eu.ydp.gwtutil.client.util.RandomWrapper;

public class ProgressAsset {

	@Inject
	private RandomWrapper random;

	private SimpleRangeMap<Integer, List<ShowImageDTO>> ranges = SimpleRangeMap.<Integer, List<ShowImageDTO>> create();

	private int id;

	public ShowImageDTO getImageForProgress(int progress) {
		List<ShowImageDTO> dtos = ranges.get(progress);
		id = random.nextInt(dtos.size() - 1);
		return dtos.get(id);
	}

	public int getId() {
		return id;
	}

	public void add(Range<Integer> range, List<ShowImageDTO> dtos) {
		ranges.put(range, dtos);
	}
}
