package eu.ydp.empiria.player.client.module.colorfill.presenter;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

@RunWith(MockitoJUnitRunner.class)
public class ResponseAnswerByViewBuilderTest {

	private ResponseAnswerByViewBuilder answerByViewBuilder;
	private ResponseUserAnswersConverter responseUserAnswersConverter;
	@Mock private ColorfillInteractionView interactionView;
	/*
	@Before
	public void setUp() throws Exception {
		responseUserAnswersConverter = new ResponseUserAnswersConverter();
		answerByViewBuilder = new ResponseAnswerByViewBuilder(interactionView, responseUserAnswersConverter);
	}

	@Test
	public void shouldGrabColorsOfAllAreasAndBuildResponseFromColloredOnes() throws Exception {
		//when
		Area area1 = new Area(1, 1);
		Area notColoredArea = new Area(2, 2);
		Area area3 = new Area(3, 3);
		List<Area> areas = Lists.newArrayList(area1, notColoredArea, area3);
		
		ColorModel color1 = ColorModel.createFromRgbString("ff0000");
		when(interactionView.getColor(area1))
			.thenReturn(color1);
		
		ColorModel transparentColor = ColorModel.createFromRgba(0, 0, 0, 0);
		when(interactionView.getColor(notColoredArea))
		.thenReturn(transparentColor);
		
		ColorModel color3 = ColorModel.createFromRgbString("0000ff");
		when(interactionView.getColor(area3))
		.thenReturn(color3);
		
		//when
		List<String> userAnswers = answerByViewBuilder.buildNewResponseAnswersByCurrentImage(areas);
		
		//then
		assertThat(userAnswers).containsOnly("1,1 ff0000", "3,3 0000ff");
	}*/
}
