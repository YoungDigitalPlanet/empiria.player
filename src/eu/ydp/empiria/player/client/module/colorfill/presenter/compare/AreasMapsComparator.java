package eu.ydp.empiria.player.client.module.colorfill.presenter.compare;

import java.util.Map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class AreasMapsComparator {
	
	public AreasMapComparationResult findDifference(Map<Area, ColorModel> previous, Map<Area, ColorModel> current){
		MapDifference<Area, ColorModel> differences = Maps.difference(previous, current);
		
		checkDifferencesCount(differences);
		
		if (isAdded(differences)){
			return getAdded(differences);
		}
		if (isRemoved(differences)){
			return getRemoved(differences);
		} 
		if (isChanged(differences)){
			return getChanged(differences);
		}
		
		return AreasMapComparationResult.ofSame();
	}
	
	private void checkDifferencesCount(MapDifference<Area, ColorModel> differences) {
		int differencesCount = differences.entriesDiffering().size() + differences.entriesOnlyOnLeft().size() + differences.entriesOnlyOnRight().size();
		if (differencesCount > 1){
			throw new IllegalArgumentException("There must be at most one difference between previous and currrent map.");
		}
	}

	private boolean isChanged(MapDifference<Area, ColorModel> differences) {
		return !differences.entriesDiffering().isEmpty();
	}

	private AreasMapComparationResult getChanged(MapDifference<Area, ColorModel> differences) {
		Area area = differences.entriesDiffering().keySet().iterator().next();
		return AreasMapComparationResult.ofAddedOrChanged(area);
	}
	
	private boolean isRemoved(MapDifference<Area, ColorModel> differences) {
		return !differences.entriesOnlyOnLeft().isEmpty();
	}

	private AreasMapComparationResult getRemoved(MapDifference<Area, ColorModel> differences) {
		Area area = differences.entriesOnlyOnLeft().keySet().iterator().next();
		return AreasMapComparationResult.ofRemoved(area);
	}
	
	private boolean isAdded(MapDifference<Area, ColorModel> differences) {
		return !differences.entriesOnlyOnRight().isEmpty();
	}

	private AreasMapComparationResult getAdded(MapDifference<Area, ColorModel> differences) {
		Area area = differences.entriesOnlyOnRight().keySet().iterator().next();
		return AreasMapComparationResult.ofAddedOrChanged(area);
	}
}
