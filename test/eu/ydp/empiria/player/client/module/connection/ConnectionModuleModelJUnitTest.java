package eu.ydp.empiria.player.client.module.connection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.xml.XMLParser;

public class ConnectionModuleModelJUnitTest {

	private ConnectionModuleModel connectionModuleModel;

	@Before
	public void init() {
		connectionModuleModel = new ConnectionModuleModel(mockResponse(), mock(ResponseModelChangeListener.class));
	}
	
	private Response mockResponse() {
		StringBuilder builder = new StringBuilder();
		builder.append("<responseDeclaration baseType=\"directedPair\" cardinality=\"multiple\" identifier=\"CONNECTION_RESPONSE_1\">");
		builder.append("		<correctResponse>");
		builder.append("			<value>CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		builder.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		builder.append("		</correctResponse>");
		builder.append("</responseDeclaration>");
		
		return new Response(XMLParser.parse(builder.toString()).getDocumentElement());
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
