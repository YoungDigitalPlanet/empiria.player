package eu.ydp.empiria.player.client.module.info;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class VariableInterpreterJUnitTest {

	private VariableInterpreter interpreter;

	private DataSourceDataSupplier sourceSupplier;

	private SessionDataSupplier sessionSupplier;

	@Before
	public void initialize() {
		sourceSupplier = mock(DataSourceDataSupplier.class);
		sessionSupplier = mock(SessionDataSupplier.class);
		interpreter = new VariableInterpreter(sourceSupplier, sessionSupplier);
	}

	@Test
	public void shouldReturnDefaulValues(){	
		ItemSessionDataSocket itemSessionDataSocket = mock(ItemSessionDataSocket.class);
		AssessmentSessionDataSocket assessmentSessionDataSocket = mock(AssessmentSessionDataSocket.class);
		VariableProviderSocket variableSocket = mock(VariableProviderSocket.class);
		int refItemIndex = 0;
		
		when(sourceSupplier.getAssessmentTitle()).thenReturn("Lesson 1");
		when(sourceSupplier.getItemTitle(anyInt())).thenReturn("Page 1");
		when(sourceSupplier.getItemsCount()).thenReturn(1);
		when(itemSessionDataSocket.getVariableProviderSocket()).thenReturn(variableSocket);
		
		when(sessionSupplier.getItemSessionDataSocket(anyInt())).thenReturn(itemSessionDataSocket);
		when(sessionSupplier.getAssessmentSessionDataSocket()).thenReturn(assessmentSessionDataSocket);
		when(assessmentSessionDataSocket.getVariableProviderSocket()).thenReturn(variableSocket);
		
		List<ValueInfo> infos = Lists.newArrayList(
					ValueInfo.create("$[item.title]", "Page 1"),
					ValueInfo.create("$[test.title]", "Lesson 1"), 
					ValueInfo.create("$[item.index]", "1"),
					ValueInfo.create("$[item.page_num]", "1"),
					ValueInfo.create("$[item.page_count]", "1"),
					ValueInfo.create("$[item.todo]", "0"),
					ValueInfo.create("$[item.done]", "0"),
					ValueInfo.create("$[item.checks]", "0"),
					ValueInfo.create("$[item.mistakes]", "0"),
					ValueInfo.create("$[item.show_answers]", "0"),
					ValueInfo.create("$[item.reset]", "0"),
					ValueInfo.create("$[item.result]", "0"),
					ValueInfo.create("$[test.title]", "Lesson 1"),
					ValueInfo.create("$[test.todo]", "0"),
					ValueInfo.create("$[test.done]", "0"),
					ValueInfo.create("$[test.checks]", "0"),
					ValueInfo.create("$[test.mistakes]", "0"),
					ValueInfo.create("$[test.show_answers]", "0"),
					ValueInfo.create("$[test.reset]", "0"),
					ValueInfo.create("$[test.result]", "0")
				);		
		
		for(ValueInfo info: infos){
			assertInfo(info, refItemIndex);
		}		
	}

	@Test
	public void shouldReturnVariableValue() {
		VariableProviderSocket variableProviderSocket = mock(VariableProviderSocket.class);
		Variable variable = mock(Variable.class);

		when(variable.getValuesShort()).thenReturn("3");
		when(variableProviderSocket.getVariableValue("existingVariable")).thenReturn(variable);

		assertThat(interpreter.getVariableValue(variableProviderSocket, "notExistingName", "0"), is(equalTo("0")));
		assertThat(interpreter.getVariableValue(variableProviderSocket, "existingVariable", "0"), is(equalTo("3")));
	}
	
	@Test
	public void shouldCountResult(){
		assertThat(interpreter.countResult(0, 10), is(equalTo(0)));
		assertThat(interpreter.countResult(2, 0), is(equalTo(0)));
		assertThat(interpreter.countResult(25, 50), is(equalTo(50)));
		assertThat(interpreter.countResult(1, 3), is(equalTo(33)));
		assertThat(interpreter.countResult(2, 3), is(equalTo(66)));
		assertThat(interpreter.countResult(10, 10), is(equalTo(100)));
	}

	private void assertInfo(ValueInfo info, int refItemIndex) {
		String template = "This is %1$s.";
		String content = String.format(template, info.getContent());
		String expectedValue = String.format(template, info.getExpectedValue());

		assertThat(info.getContent(), interpreter.replaceTemplates(content, refItemIndex), is(equalTo(expectedValue)));
	}

	private static class ValueInfo {

		private String content;
		private String expectedValue;

		public static ValueInfo create(String content, String expectedValue) {
			return new ValueInfo(content, expectedValue);
		}

		public ValueInfo(String content, String expectedValue) {
			this.content = content;
			this.expectedValue = expectedValue;
		}

		public String getContent() {
			return content;
		}

		public String getExpectedValue() {
			return expectedValue;
		}

	}

}
