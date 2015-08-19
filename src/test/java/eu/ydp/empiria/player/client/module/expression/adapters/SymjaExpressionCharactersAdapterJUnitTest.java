package eu.ydp.empiria.player.client.module.expression.adapters;

import com.google.common.collect.ImmutableMap;
import eu.ydp.empiria.player.client.module.expression.PipedReplacementsParser;
import eu.ydp.empiria.player.client.style.StyleSocket;
import org.junit.Before;
import org.junit.Test;

import static eu.ydp.empiria.player.client.module.expression.adapters.ExpressionCharacterMappingProvider.SELECTOR;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_EXPRESSION_MAPPING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SymjaExpressionCharactersAdapterJUnitTest {

    private SymjaExpressionCharactersAdapter symjaExpressionAdapter;
    private StyleSocket styleSocket;

    @Before
    public void setUp() {
        styleSocket = mock(StyleSocket.class);
        PipedReplacementsParser expressionReplacementsParser = new PipedReplacementsParser();
        ExpressionCharactersMappingParser parser = new ExpressionCharactersMappingParser(expressionReplacementsParser);
        ExpressionCharacterMappingProvider replacementsProvider = new ExpressionCharacterMappingProvider(styleSocket, parser);
        DefaultExpressionCharactersAdapter defaultAdapter = new DefaultExpressionCharactersAdapter(replacementsProvider);
        symjaExpressionAdapter = new SymjaExpressionCharactersAdapter(defaultAdapter);
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
    public void testCommaIntoDot() {
        String expression = "2,2+2,3>=4,5";

        String result = symjaExpressionAdapter.process(expression);

        assertEquals("2.2+2.3>=4.5", result);
    }

    @Test
    public void testDivide() {
        String expression = "(2:2)+(2:2)=2";

        String result = symjaExpressionAdapter.process(expression);

        assertEquals("(2/2)+(2/2)==2", result);
    }

    @Test
    public void testReplacements() {
        // given
        when(styleSocket.getStyles(SELECTOR)).thenReturn(ImmutableMap.of(EMPIRIA_EXPRESSION_MAPPING, " ×|*|:,;,÷|/|≤|<= "));
        String expression = "(2×2)+(2÷2)≤2";

        // when
        String result = symjaExpressionAdapter.process(expression);

        // then
        assertEquals("(2*2)+(2/2)<=2", result);
    }
}
