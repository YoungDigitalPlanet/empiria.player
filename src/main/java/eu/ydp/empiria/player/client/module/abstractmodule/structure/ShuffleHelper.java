package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import eu.ydp.gwtutil.client.collections.RandomizedSet;

public class ShuffleHelper {

	public <B extends ModuleBean & HasShuffle, I extends HasFixed> List<I> randomizeIfShould(B bean, List<I> associableChoices) {

		List<I> resultList;
		if (bean.isShuffle()) {
			resultList = randomize(associableChoices);
		} else {
			resultList = associableChoices;
		}
		return resultList;
	}

	private <I extends HasFixed> List<I> randomize(List<I> associableChoices) {

		Map<Integer, I> fixedItemsMap = new TreeMap<Integer, I>();
		List<I> randomItems = new ArrayList<I>();
		List<I> resultList = new ArrayList<I>(associableChoices.size());

		devideByFixedProperty(associableChoices, fixedItemsMap, randomItems);

		addRandomItems(resultList, randomItems);
		addFixedItems(resultList, fixedItemsMap);

		return resultList;
	}

	private <I extends HasFixed> void devideByFixedProperty(List<I> source, Map<Integer, I> fixedItemsMap, List<I> randomItems) {
		for (int x = 0; x < source.size(); ++x) {
			I item = source.get(x);
			if (item.isFixed()) {
				fixedItemsMap.put(x, item);
			} else {
				randomItems.add(item);
			}
		}
	}

	private <I extends HasFixed> void addRandomItems(List<I> target, List<I> source) {
		if (!source.isEmpty()) {
			RandomizedSet<I> randomSet = new RandomizedSet<I>(source);
			while (randomSet.hasMore()) {
				target.add(randomSet.pull());
			}
		}
	}

	private <I extends HasFixed> void addFixedItems(List<I> target, Map<Integer, I> source) {
		for (Map.Entry<Integer, I> fixedEntry : source.entrySet()) {
			target.add(fixedEntry.getKey(), fixedEntry.getValue());
		}
	}
}
