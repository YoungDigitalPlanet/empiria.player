package eu.ydp.empiria.player.client.module.expression.evaluate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class CommutationEvaluatorJUnitTest extends AbstractTestWithMocksBase {

	private static int COUNTER;

	private CommutationEvaluator eval;

	@Override
	public void setUp() {
		super.setUp(CommutationEvaluator.class);
		eval = injector.getInstance(CommutationEvaluator.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateCorrect() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(correctResponse(), correctResponse()),
				Sets.newHashSet(correctResponse(), correctResponse(), correctResponse()),
				Sets.newHashSet(correctResponse(), correctResponse(), correctResponse(), correctResponse()));
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateCorrect_commutated() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(response(1, 3), response(2, 4)),
				Sets.newHashSet(response(3, 2), response(4, 1)),
				Sets.newHashSet(response(1, 4), response(2, 2), response(3, 1), response(4, 3)));
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateCorrect_commutation_threefold() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(response(1, 3), response(2, 4)),
				Sets.newHashSet(response(3, 6), response(4, 5)),
				Sets.newHashSet(response(5, 1), response(6, 2)));
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(true));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateWrong_someWrongs() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(correctResponse(), correctResponse()),
				Sets.newHashSet(correctResponse(), correctResponse(), correctResponse()),
				Sets.newHashSet(correctResponse(), correctResponse(), correctResponse(), wrongResponse()));
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateWrong_commutated() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(response(1, 3), response(2, 4)),
				Sets.newHashSet(response(3, 2), response(4, 2)),
				Sets.newHashSet(response(1, 3), response(2, 4), response(3, 2), response(4, 1)));
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(false));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void evaluateWrong_allWrongs() {
		// given
		ExpressionBean bean = new ExpressionBean();
		List<Set<Response>> setsOfResponses = Lists.<Set<Response>> newArrayList(
				Sets.newHashSet(wrongResponse(), wrongResponse()),
				Sets.newHashSet(wrongResponse(), wrongResponse(), wrongResponse()),
				Sets.newHashSet(wrongResponse(), wrongResponse(), wrongResponse(), wrongResponse()) );
		bean.setSetsOfResponses(setsOfResponses);

		// when
		boolean result = eval.evaluate(bean);

		// then
		assertThat(result, equalTo(false));
	}

	private Response response(int correct, int user) {
		CorrectAnswers correctAnswers = new CorrectAnswers();
		correctAnswers.add(new ResponseValue("answer_" + correct));
		List<String> values = Lists.newArrayList("answer_" + user);
		Response response = new Response(correctAnswers, values, Lists.<String> newArrayList(), "", Evaluate.DEFAULT, BaseType.STRING, Cardinality.SINGLE,
				CountMode.SINGLE, new ExpressionBean(), CheckMode.EXPRESSION);
		return response;
	}

	private Response correctResponse() {
		Response response = response(COUNTER, COUNTER);
		COUNTER++;
		return response;
	}

	private Response wrongResponse() {
		Response response = response(COUNTER++, COUNTER++);
		return response;
	}

}
