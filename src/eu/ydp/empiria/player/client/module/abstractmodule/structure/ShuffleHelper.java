package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import eu.ydp.gwtutil.client.collections.RandomizedSet;

public class ShuffleHelper {

	public <B extends ModuleBean & HasShuffle, I extends HasFixed> List<I> randomize(B bean, List<I> associableChoices) {
		List<I> resultList = associableChoices;
		if (bean.isShuffle()) {
			resultList = new ArrayList<I>(associableChoices.size());
			List<I> copyList = new ArrayList<I>(associableChoices);
			Map<Integer, I> fixedMap = new TreeMap<Integer, I>();
			TreeSet<Integer> reverseOrder = new TreeSet<Integer>(Collections.reverseOrder());
			for (int x = 0; x < associableChoices.size(); ++x) {
				I item = associableChoices.get(x);
				if (item.isFixed()) {
					fixedMap.put(x, item);
					reverseOrder.add(x);
				}
			}

			for (Integer index : reverseOrder) {
				copyList.remove(index.intValue());
			}

			if (!copyList.isEmpty()) {
				RandomizedSet<I> randomSet = new RandomizedSet<I>(copyList);
				while (randomSet.hasMore()) {
					resultList.add(randomSet.pull());
				}
			}
			for (Map.Entry<Integer, I> fixedEntry : fixedMap.entrySet()) {
				resultList.add(fixedEntry.getKey(), fixedEntry.getValue());
			}
		}
		return resultList;
	}
}
