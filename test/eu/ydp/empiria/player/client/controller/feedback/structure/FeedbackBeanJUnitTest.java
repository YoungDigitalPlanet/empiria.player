package eu.ydp.empiria.player.client.controller.feedback.structure;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlActionSource;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

public class FeedbackBeanJUnitTest extends AbstractJAXBTestBase<FeedbackBean>  {
	
	private FeedbackBean feedback;
	
	private final String xmlString = "<feedback>" +
									"<condition>" +
										"<and>" +
											"<countCondition count=\"3\" operator=\"==\">" +
												"<propertyCondition operator=\"&gt;=\" property=\"wrong\" value=\"1\"/>" +
												"<propertyCondition property=\"ok\"/>" +
											"</countCondition>" +
										"</and>" +
									"</condition>" +
									"<action>" +
										"<showText>testowy tekst</showText>" +
										"<showUrl href=\"sound.mp3\" type=\"narration\">" +
											"<source src=\"sound.mp3\" type=\"audio/mp4\"/>" +
											"<source src=\"sound.ogg\" type=\"audio/ogg\"/>" +
										"</showUrl>" +
										"<showUrl href=\"video.swf\" type=\"video\" />" +
									"</action>" +
								"</feedback>";
	
	@Before
	public void createFeedback() {
		feedback = createBeanFromXMLString(xmlString);
	}
	
	@Test
	public void shouldHaveActionAndCondition() {
		assertThat(feedback.getCondition(), notNullValue());
		assertThat(feedback.getAction(), notNullValue());
	}

	@Test
	public void shouldHaveFirstLevelConditions() {
		assertThat(feedback.getCondition(), instanceOf(AndConditionBean.class));
	}
	
	@Test
	public void shouldHaveCountCondition() {
		AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
		assertThat(andConditionBean.getAllConditions().get(0), instanceOf(CountConditionBean.class));
	}
	
	@Test
	public void shouldHaveCorrectAttributesInCountCondition() {
		AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
		CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
		assertThat(countConditionBean.getCount(), is(3));
		assertThat(countConditionBean.getOperator(), is("=="));
	}
	
	@Test
	public void shouldHavePropertyCondition() {
		AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
		CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
		assertThat(countConditionBean.getAllConditions().get(0), instanceOf(PropertyConditionBean.class));
	}
	
	@Test
	public void shouldHaveCorrectAttributesInPropertyCondition() {
		AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
		CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
		PropertyConditionBean propertyConditionBean = (PropertyConditionBean) countConditionBean.getAllConditions().get(0);
		assertThat(propertyConditionBean.getProperty(), is("wrong"));
		assertThat(propertyConditionBean.getValue(), is("1"));
		assertThat(propertyConditionBean.getOperator(), is(">="));
	}
	
	@Test
	public void shouldHaveCorrectDefaultAttributesInPropertyCondition() {
		AndConditionBean andConditionBean = (AndConditionBean) feedback.getCondition();
		CountConditionBean countConditionBean = (CountConditionBean) andConditionBean.getAllConditions().get(0);
		PropertyConditionBean propertyConditionBean = (PropertyConditionBean) countConditionBean.getAllConditions().get(1);
		assertThat(propertyConditionBean.getProperty(), is("ok"));
		assertThat(propertyConditionBean.getValue(), is("true"));
		assertThat(propertyConditionBean.getOperator(), is("=="));
	}
	
	@Test
	public void shouldHaveShowTextAction() {
		List<FeedbackAction> allActions = feedback.getActions();
		assertThat(allActions.get(0), instanceOf(ShowTextAction.class));
	}
	
	@Test
	public void shouldHaveCorrectValueInShowTextAction() {
		List<FeedbackAction> allActions = feedback.getActions();
		ShowTextAction showTextAction = (ShowTextAction) allActions.get(0);
		assertThat(showTextAction.getText(), is("testowy tekst"));
	}
	
	@Test
	public void shouldHaveShowUrlActions() {
		List<FeedbackAction> allActions = feedback.getActions();
		assertThat(allActions.get(1), instanceOf(ShowUrlAction.class));
		assertThat(allActions.get(2), instanceOf(ShowUrlAction.class));
	}
	
	@Test
	public void shouldHaveCorrectAttributesInShowUrlActions() {
		List<FeedbackAction> allActions = feedback.getActions();
		ShowUrlAction firstShowUrlAction = (ShowUrlAction) allActions.get(1);
		ShowUrlAction secondShowUrlAction = (ShowUrlAction) allActions.get(2);
		assertThat(firstShowUrlAction.getHref(), is("sound.mp3"));
		assertThat(firstShowUrlAction.getType(), is("narration"));
		assertThat(secondShowUrlAction.getHref(), is("video.swf"));
		assertThat(secondShowUrlAction.getType(), is("video"));
	}
	
	@Test
	public void shouldHaveCorrectSourcesInShowUrlActionOfNarrationType() {
		List<FeedbackAction> allActions = feedback.getActions();
		ShowUrlAction firstShowUrlAction = (ShowUrlAction) allActions.get(1);
		List<ShowUrlActionSource> sources = firstShowUrlAction.getSources();
		ShowUrlActionSource firstSource = sources.get(0);
		ShowUrlActionSource secondSource = sources.get(1);
		assertThat(firstSource.getSrc(), is("sound.mp3"));
		assertThat(firstSource.getType(), is("audio/mp4"));
		assertThat(secondSource.getSrc(), is("sound.ogg"));
		assertThat(secondSource.getType(), is("audio/ogg"));
	}
}
