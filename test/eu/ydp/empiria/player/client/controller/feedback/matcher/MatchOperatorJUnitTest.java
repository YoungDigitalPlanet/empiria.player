package eu.ydp.empiria.player.client.controller.feedback.matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MatchOperatorJUnitTest {
	
	private MatchOperator equalOperator = MatchOperator.getOperator(MatchOperator.EQUAL.getOperator());
	private MatchOperator notEqualOperator = MatchOperator.getOperator(MatchOperator.NOT_EQUAL.getOperator());
	private MatchOperator greaterOperator = MatchOperator.getOperator(MatchOperator.GREATER.getOperator());
	private MatchOperator greaterEqualOperator = MatchOperator.getOperator(MatchOperator.GREATER_EQUAL.getOperator());
	private MatchOperator lessOperator = MatchOperator.getOperator(MatchOperator.LESS.getOperator());
	private MatchOperator lessEqualOperator = MatchOperator.getOperator(MatchOperator.LESS_EQUAL.getOperator());
	private MatchOperator noneOperator = MatchOperator.getOperator(MatchOperator.NONE.getOperator());
	
	// *********** Boolean
	
	@Test
	public void shouldReturnTrueWhenBooleansAreEqualUsingEqualOperator() {
		boolean match = equalOperator.match(true, true);
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.match(true, false);
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(true, true);
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenBooleansAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(true, false);
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenBooleansAreEqualUsingNoneOperator() {
		boolean match = noneOperator.match(true, false);
		assertThat(match, is(false));
	}
	
	// *********** String
	
	@Test
	public void shouldReturnTrueWhenStringsAreEqualUsingEqualOperator() {
		boolean match = equalOperator.match("first string", "first string");
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.match("first string", "second string");
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match("first string", "first string");
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenStringsAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match("first string", "second string");
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenStringsAreEqualUsingNoneOperator() {
		boolean match = noneOperator.match("first string", "first string");
		assertThat(match, is(false));
	}
	
	// *********** Integer
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingEqualOperator() {
		boolean match = equalOperator.match(new Integer(99999999), new Integer(99999999));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.match(new Integer(99999999), new Integer(99999991));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(new Integer(99999999), new Integer(99999999));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(new Integer(99999999), new Integer(99999991));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsLesserThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.match(new Integer(100), new Integer(200));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsGreaterThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.match(new Integer(200), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingLessOperator() {
		boolean match = lessOperator.match(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsLesserThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Integer(100), new Integer(200));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsGreaterThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Integer(200), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Integer(100), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsLesserThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Integer(100), new Integer(200));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsGreaterThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Integer(200), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstIntegerIsLesserThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Integer(100), new Integer(200));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstIntegerIsGreaterThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Integer(200), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenIntegersAreEqualUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Integer(100), new Integer(100));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenIntegersAreEqualUsingNoneOperator() {
		boolean match = noneOperator.match(new Integer(100), new Integer(100));
		assertThat(match, is(false));
	}
	
	// *********** Double
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingEqualOperator() {
		boolean match = equalOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreNotEqualUsingEqualOperator() {
		boolean match = equalOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreNotEqualUsingNotEqualOperator() {
		boolean match = notEqualOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsLesserThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsGreaterThanTheSecondOneUsingLessOperator() {
		boolean match = lessOperator.match(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingLessOperator() {
		boolean match = lessOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsLesserThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsGreaterThanTheSecondOneUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingLessEqualOperator() {
		boolean match = lessEqualOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsLesserThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsGreaterThanTheSecondOneUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublessAreEqualUsingGreaterOperator() {
		boolean match = greaterOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnFalseWhenFirstDoubleIsLesserThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Double(1.555555), new Double(1.555556));
		assertThat(match, is(false));
	}
	
	@Test
	public void shouldReturnTrueWhenFirstDoubleIsGreaterThanTheSecondOneUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Double(1.555556), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenDoublesAreEqualUsingGreaterEqualOperator() {
		boolean match = greaterEqualOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(true));
	}
	
	@Test
	public void shouldReturnFalseWhenDoublesAreEqualUsingNoneOperator() {
		boolean match = noneOperator.match(new Double(1.555555), new Double(1.555555));
		assertThat(match, is(false));
	}
}
