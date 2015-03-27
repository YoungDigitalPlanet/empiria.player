package eu.ydp.empiria.player.client.module.connection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModuleModelJUnitTest {

	private ConnectionModuleModel connectionModuleModel;

	// private final ResponseNodeParser responseNodeParser = new
	// ResponseNodeParser();

	@Before
	public void init() {
		connectionModuleModel = new ConnectionModuleModel(mockResponse(), mock(ResponseModelChangeListener.class));
	}

	private Response mockResponse() {
		ResponseBuilder responseBuilder = new ResponseBuilder();

		responseBuilder.withCorrectAnswers("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1", "CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");

		return responseBuilder.build();
	}

	@Test
	public void shouldParseResponseAndConvertToInternalKeyValueFormat() {
		List<KeyValue<String, String>> correctAnswers = connectionModuleModel.getCorrectAnswers();

		assertThat(correctAnswers.size(), is(equalTo(2)));
		assertThat(correctAnswers.get(0).getKey(), is(equalTo("CONNECTION_RESPONSE_1_0")));
		assertThat(correctAnswers.get(0).getValue(), is(equalTo("CONNECTION_RESPONSE_1_1")));
		assertThat(correctAnswers.get(1).getKey(), is(equalTo("CONNECTION_RESPONSE_1_3")));
		assertThat(correctAnswers.get(1).getValue(), is(equalTo("CONNECTION_RESPONSE_1_4")));
	}

	@Test
	public void checkUserResonseContainsAnswer() {
		connectionModuleModel.addAnswer("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
		connectionModuleModel.addAnswer("CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");

		boolean check = connectionModuleModel.checkUserResonseContainsAnswer("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");

		assertThat(check, is(equalTo(true)));
	}
}
