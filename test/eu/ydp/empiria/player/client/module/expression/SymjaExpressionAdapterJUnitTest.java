package eu.ydp.empiria.player.client.module.expression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.expression.adapters.SymjaExpressionCharactersAdapter;

public class SymjaExpressionAdapterJUnitTest extends AbstractTestWithMocksBase {
	
	private SymjaExpressionCharactersAdapter symjaExpressionAdapter;

	@Override
	public void setUp() {
		super.setUp();
		symjaExpressionAdapter = injector.getInstance(SymjaExpressionCharactersAdapter.class);
	}	
	
	@Test
	public void testOperatorsEquation() {
		String expression = "2+2=4";
		
		String result = symjaExpressionAdapter.process(expression);
		
		assertEquals("2+2==4", result);
	}
	
	@Test
	public void testOperatorsInequation() {
		String expression = "2+2>=4";
		
		String result = symjaExpressionAdapter.process(expression);
		
		assertEquals("2+2>=4", result);
	}

	@Test
	public void testDivide() {
		String expression = "2:2=1";
		
		String result = symjaExpressionAdapter.process(expression);
		
		assertEquals("2/2==1", result);
	}
}
