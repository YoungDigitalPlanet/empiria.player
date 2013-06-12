package eu.ydp.empiria.player.client.module.colorfill;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseUserAnswersConverter;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;

@RunWith(MockitoJUnitRunner.class)
public class ColorfillModelProxyJUnitTest {

	@InjectMocks
	private ColorfillModelProxy modelProxy;
	@Mock
	private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
	@Mock
	private ColorfillInteractionModuleModel colorfillInteractionModuleModel;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private ColorfillBeanProxy beanProxy;
	@Mock
	private ResponseUserAnswersConverter responseUserAnswersConverter;
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateUserAnswers(){
		// given
		List<String> list = Lists.newArrayList("a");
		when(responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(anyList())).thenReturn(list);
		
		// when
		modelProxy.updateUserAnswers();
		
		// then
		verify(colorfillInteractionModuleModel).setNewUserAnswers(list);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getUserAnswers(){
		// given
		Map<Area, ColorModel> map = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3));
		when(responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(anyList())).thenReturn(map);
		
		// when
		Map<Area, ColorModel> result = modelProxy.getUserAnswers();
		
		// then
		assertThat(result).isEqualTo(map);
	}
	
}
