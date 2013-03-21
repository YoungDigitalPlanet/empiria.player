package eu.ydp.empiria.player.client.controller.variables.processor.global;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class GlobalVariablesProcessorJUnitTest {

	private GlobalVariablesProcessor globalVariablesProcessor;
	private ExtractingFunctionsProvider extractingFunctionsProvider;

	@Before
	public void setUp() throws Exception {
		extractingFunctionsProvider = new ExtractingFunctionsProvider();
		globalVariablesProcessor = new GlobalVariablesProcessor(extractingFunctionsProvider);
	}

	@Test
	public void shouldCalculateSumOfTodo() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withTodo(2)
					.build(), 
				newProcessingResults().withTodo(3)
					.build());

		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);

		assertThat(globalVariables.getTodo(), equalTo(5));
	}
	
	@Test
	public void shouldCalculateGlobalSumOfErrors() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withErrors(4)
				.build(), 
				newProcessingResults().withErrors(5)
				.build());
		
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);
		
		assertThat(globalVariables.getErrors(), equalTo(9));
	}
	
	@Test
	public void shouldCalculateGlobalSumOfDone() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withDone(1)
				.build(), 
				newProcessingResults().withDone(2)
				.build());
		
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);
		
		assertThat(globalVariables.getDone(), equalTo(3));
	}
	
	@Test
	public void shouldCalculateGlobalSumOfMistakes() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withMistakes(5)
				.build(), 
				newProcessingResults().withMistakes(2)
				.build());
		
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);
		
		assertThat(globalVariables.getMistakes(), equalTo(7));
	}
	
	@Test
	public void shouldSetGlobalLastmistakenWhenEvenOneLocalIsSet() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withLastmistaken(false)
				.build(), 
				newProcessingResults().withLastmistaken(false)
				.build(),
				newProcessingResults().withLastmistaken(true)
				.build());
		
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);
		
		assertThat(globalVariables.isLastmistaken(), equalTo(true));
	}
	
	@Test
	public void shouldNotSetGlobalLastmistakenWhenAllLocalAreWithoutLastmistaken() throws Exception {
		Collection<DtoModuleProcessingResult> modulesProcessingResults = Lists.newArrayList(
				newProcessingResults().withLastmistaken(false)
				.build(), 
				newProcessingResults().withLastmistaken(false)
				.build(),
				newProcessingResults().withLastmistaken(false)
				.build());
		
		GlobalVariables globalVariables = globalVariablesProcessor.calculateGlobalVariables(modulesProcessingResults);
		
		assertThat(globalVariables.isLastmistaken(), equalTo(false));
	}
	
	

	private DtoModuleProcessingResultBuilder newProcessingResults() {
		return new DtoModuleProcessingResultBuilder();
	}
}
