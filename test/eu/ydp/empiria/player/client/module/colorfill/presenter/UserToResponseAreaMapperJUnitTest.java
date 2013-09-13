package eu.ydp.empiria.player.client.module.colorfill.presenter;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.colorfill.ColorfillModelProxy;
import eu.ydp.empiria.player.client.module.colorfill.presenter.compare.AreasMapsComparator;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;


public class UserToResponseAreaMapperJUnitTest {

	private UserToResponseAreaMapper areaMapper;
	private ColorfillModelProxy modelProxy; 
	
	@Before
	public void setUp(){
		modelProxy = mock(ColorfillModelProxy.class);
		areaMapper = new UserToResponseAreaMapper(modelProxy, new AreasMapsComparator());
	}
	
	@Test
	public void mapResponseToUser_addArea(){
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);
		
		Area responseArea1 = new Area(3, 4);
		Area userArea1 = new Area(13, 14);
		ColorModel colorModel1 = ColorModel.createFromRgba(11, 12, 13, 14);
		
		Map<Area, ColorModel> userAnswersState0 = ImmutableMap.of(responseArea0, colorModel0);
		Map<Area, ColorModel> userAnswersState1 = ImmutableMap.of(responseArea0, colorModel0, responseArea1, colorModel1);
		
		// given
		simulateUserAnswersChange(userArea0, userAnswersState0);
		simulateUserAnswersChange(userArea1, userAnswersState1);

		// when
		Iterable<Area> userAreaFound = areaMapper.mapResponseToUser(Lists.newArrayList(responseArea0, responseArea1));
		
		// then
		assertThat(userAreaFound).containsExactly(userArea0, userArea1);
	}
	
	@Test
	public void mapResponseToUser_changeArea(){
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		Area userArea1 = new Area(21, 22);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);
		ColorModel colorModel1 = ColorModel.createFromRgba(11, 12, 13, 14);
		
		Map<Area, ColorModel> userAnswersState0 = ImmutableMap.of(responseArea0, colorModel0);
		Map<Area, ColorModel> userAnswersState1 = ImmutableMap.of(responseArea0, colorModel1);
		
		// given
		simulateUserAnswersChange(userArea0, userAnswersState0);
		simulateUserAnswersChange(userArea1, userAnswersState1);
		
		// when
		Iterable<Area> userAreaFound = areaMapper.mapResponseToUser(Lists.newArrayList(responseArea0));
		
		// then
		assertThat(userAreaFound).containsExactly(userArea1);
	}
	
	@Test
	public void mapResponseToUser_changeArea_sameColor(){
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		Area userArea1 = new Area(21, 22);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);
		
		Map<Area, ColorModel> userAnswersState = ImmutableMap.of(responseArea0, colorModel0);
		
		// given
		simulateUserAnswersChange(userArea0, userAnswersState);
		simulateUserAnswersChange(userArea1, userAnswersState);
		
		// when
		Iterable<Area> userAreaFound = areaMapper.mapResponseToUser(Lists.newArrayList(responseArea0));
		
		// then
		assertThat(userAreaFound).containsExactly(userArea0);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapResponseToUser_remove(){
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		Area userArea1 = new Area(21, 22);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);
		
		Map<Area, ColorModel> userAnswersState0 = ImmutableMap.of(responseArea0, colorModel0);
		Map<Area, ColorModel> userAnswersState1 = ImmutableMap.of();
		
		// given
		simulateUserAnswersChange(userArea0, userAnswersState0);
		simulateUserAnswersChange(userArea1, userAnswersState1);
		
		// when
		Iterable<Area> userAreaFound = areaMapper.mapResponseToUser(Collections.EMPTY_LIST);
		
		// then
		assertThat(userAreaFound).isEmpty();
	}
	
	@Test
	public void mapResponseToUser_reset_sameArea(){
		Area responseArea = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		Area userArea1 = new Area(11, 12);
		ColorModel colorModel = ColorModel.createFromRgba(1, 2, 3, 4);
		
		Map<Area, ColorModel> userAnswersState = ImmutableMap.of(responseArea, colorModel);
		
		// given
		simulateUserAnswersChange(userArea0, userAnswersState);
		areaMapper.reset();
		simulateUserAnswersChange(userArea1, userAnswersState);
		// when
		Iterable<Area> userAreaFound = areaMapper.mapResponseToUser(Lists.newArrayList(responseArea));
		// then
		assertThat(userAreaFound).containsExactly(userArea1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void mapResponseToUser_invalidAnswer(){
		// given
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);

		Area responseAreaInvalid = new Area(3, 4);
		
		Map<Area, ColorModel> userAnswersState0 = ImmutableMap.of(responseArea0, colorModel0);
		simulateUserAnswersChange(userArea0, userAnswersState0);
		
		// when
		areaMapper.mapResponseToUser(Lists.newArrayList(responseArea0, responseAreaInvalid));
	}
	
	@Test
	public void mapResponseToUser_emptyAnswer(){
		// given
		Area responseArea0 = new Area(1, 2);
		Area userArea0 = new Area(11, 12);
		ColorModel colorModel0 = ColorModel.createFromRgba(1, 2, 3, 4);
		
		Map<Area, ColorModel> userAnswersState0 = ImmutableMap.of(responseArea0, colorModel0);
		simulateUserAnswersChange(userArea0, userAnswersState0);
		
		// when
		Iterable<Area> result = areaMapper.mapResponseToUser(Lists.<Area>newArrayList());
		
		assertThat(result).isEmpty();
	}

	private void simulateUserAnswersChange(Area userArea, Map<Area, ColorModel> userAnswersAfterChange) {
		when(modelProxy.getUserAnswers()).thenReturn(userAnswersAfterChange);
		areaMapper.updateMappings(userArea);
	}
}
