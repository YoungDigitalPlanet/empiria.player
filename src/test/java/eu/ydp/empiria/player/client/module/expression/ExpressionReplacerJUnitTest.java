package eu.ydp.empiria.player.client.module.expression;

import com.google.common.collect.ImmutableMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ExpressionReplacerJUnitTest {

    private ExpressionReplacer expressionReplacer = new ExpressionReplacer();

    @Test
    public void eligibleForReplacement() {
        // given
        expressionReplacer.useReplacements(ImmutableMap.of("a", "b", "c", "dx"));
        final String GIVEN_TEXT = "a";

        // when
        boolean checkResult = expressionReplacer.isEligibleForReplacement(GIVEN_TEXT);
        String replaced = expressionReplacer.replace(GIVEN_TEXT);

        // then
        assertThat(checkResult).isTrue();
        assertThat(replaced).isEqualTo("b");
    }

    @Test
    @Parameters({"x", "ax", "xa", ""})
    public void notEligibleForReplacement(final String givenText) {
        // given
        expressionReplacer.useReplacements(ImmutableMap.of("a", "b", "c", "dx"));

        // when
        boolean checkResult = expressionReplacer.isEligibleForReplacement(givenText);

        // then
        assertThat(checkResult).isFalse();
    }

}
