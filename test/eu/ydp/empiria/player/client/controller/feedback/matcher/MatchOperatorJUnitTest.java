package eu.ydp.empiria.player.client.controller.feedback.matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MatchOperatorJUnitTest {
	
	private MatchOperator equalOperator = MatchOperator.getMatchOperator(MatchOperator.EQUAL.getName());
	private MatchOperator notEqualOperator = MatchOperator.getMatchOperator(MatchOperator.NOT_EQUAL.getName());
	private MatchOperator greaterOperator = MatchOperator.getMatchOperator(MatchOperator.GREATER.getName());
	private MatchOperator greaterEqualOperator = MatchOperator.getMatchOperator(MatchOperator.GREATER_EQUAL.getName());
	private MatchOperator lessOperator = MatchOperator.getMatchOperator(MatchOperator.LESS.getName());
	private MatchOperator lessEqualOperator = MatchOperator.getMatchOperator(MatchOperator.LESS_EQUAL.getName());
	private MatchOperator noneOperator = MatchOperator.getMatchOperator(MatchOperator.NONE.getName());
	
	// *********** Boolean
	
	@Test
	public void shouldReturnTrueWhenBooleansAreEqualUsingEqualOperator() {
		boolean match = equalOperator.checkBoolean(true, true);
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.checkBoolean(true, false);
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkBoolean(true, true);
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenBooleansAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkBoolean(true, false);
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreEqualUsingNoneOperator() {
		boolean match = noneOperator.checkBoolean(true, false);
		assertThat(match, is(false));
	}
	
	// *********** String
	
	@Test
	public void shouldReturnTrueWhenStringsAreEqualUsingEqualOperator() {
		boolean match = equalOperator.checkString("first string", "first string");
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.checkString("first string", "second string");
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkString("first string", "first string");
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenStringsAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkString("first string", "second string");
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreEqualUsingNoneOperator() {
		boolean match = noneOperator.checkString("first string", "first string");
		assertThat(match, is(false));
	}
	
	// *********** Integer
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingEqualOperator() {
		boolean match = equalOperator.checkNumber(new Integer(99999999), new Integer(99999999));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.checkNumber(new Integer(99999999), new Integer(99999991));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkNumber(new Integer(99999999), new Integer(99999999));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkNumber(new Integer(99999999), new Integer(99999991));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsLesserThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Integer(100), new Integer(200));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsGreaterThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Integer(200), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsLesserThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Integer(100), new Integer(200));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsGreaterThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Integer(200), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Integer(100), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsLesserThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Integer(100), new Integer(200));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsGreaterThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Integer(200), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsLesserThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Integer(100), new Integer(200));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsGreaterThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Integer(200), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Integer(100), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingNoneOperator() {
		boolean match = noneOperator.checkNumber(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	// *********** Double
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingEqualOperator() {
		boolean match = equalOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsLesserThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsGreaterThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingLessOperator() {
		boolean match = lessOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsLesserThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsGreaterThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingLessEqualOperator() {
		boolean match = lessEqualOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsLesserThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsGreaterThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublessAreEqualUsingGreaterOperator() {
		boolean match = greaterOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsLesserThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsGreaterThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingNoneOperator() {
		boolean match = noneOperator.checkNumber(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
}
