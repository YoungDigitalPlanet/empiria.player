package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.compare.AreasMapComparationResult;
import eu.ydp.empiria.player.client.module.colorfill.presenter.compare.AreasMapsComparator;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;

public class UserToResponseAreaMapper {

	private final ColorfillModelProxy modelProxy;
	private final AreasMapsComparator comparator;
	
	private Map<Area, Area> responseToUserAreas = Maps.newHashMap();
	private Map<Area, ColorModel> previousAnswers = Maps.newHashMap();
	
	@Inject
	public UserToResponseAreaMapper(@ModuleScoped ColorfillModelProxy modelProxy, AreasMapsComparator comparator){
		this.modelProxy = modelProxy;
		this.comparator = comparator;
	}
	
	private Function<Area, Area> transformResponseToUserAreas = new Function<Area, Area>() {

		@Override
		@Nullable
		public Area apply(@Nullable Area responseArea) {
			return responseToUserAreas.get(responseArea);
		}
	};

	public void updateMappings(Area userArea) {
		Map<Area, ColorModel> currentAnswers = Collections.unmodifiableMap(modelProxy.getUserAnswers());
		AreasMapComparationResult result = comparator.findDifference(previousAnswers, currentAnswers);
		if (result.isDifference()){
			updateResponseToUserMap(userArea, result);
		}
		setPreviousAnswers(currentAnswers);
	}

	private void setPreviousAnswers(Map<Area, ColorModel> currentAnswers) {
		previousAnswers.clear();
		previousAnswers.putAll(currentAnswers);
	}

	private void updateResponseToUserMap(Area userArea, AreasMapComparationResult result) {
		Area responseArea = result.getArea();
		if (result.isAddedOrChanged()){
			responseToUserAreas.put(responseArea, userArea);
		} else {
			responseToUserAreas.remove(responseArea);
		}
	}
	
	public Iterable<Area> mapResponseToUser(Iterable<Area> areas){
		Preconditions.checkArgument(responseToUserAreas.keySet().containsAll(Sets.newHashSet(areas)));
		return Iterables.transform(areas, transformResponseToUserAreas);
	}
	
	public void reset(){
		responseToUserAreas.clear();
		previousAnswers.clear();
	}
}
