package eu.ydp.empiria.player.client.module.expression;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;


public class PipedReplacementsParserJUnitTest {
	
	private PipedReplacementsParser parser = new PipedReplacementsParser();

	@Test
	public void parse(){
		// given
		String characters = "a|b|>=|≥|x|×|*|×";
		
		// when
		Map<String, String> map = parser.parse(characters);
		
		// then
		assertThat(map).isEqualTo(ImmutableMap.of("a", "b", ">=", "≥", "x", "×", "*", "×"));
	}
	
	@Test
	public void parse_shouldTrimWhitespaces(){
		// given
		String characters = " \n\ta|b|>=|≥|x|×|*|× ";
		
		// when
		Map<String, String> map = parser.parse(characters);
		
		// then
		assertThat(map).isEqualTo(ImmutableMap.of("a", "b", ">=", "≥", "x", "×", "*", "×"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void parse_InvalidSet_oddNumberOfParts(){
		// given
		String characters = "a|b|>=|≥|x|×|*";
		
		// when
		parser.parse(characters);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parse_InvalidSet_emptyPart(){
		// given
		String characters = "a|b|>=|≥|x||*";
		
		// when
		parser.parse(characters);
	}

	@Test
	public void parse_EmptySet(){
		// given
		String characters = "";
		
		// when
		parser.parse(characters);
	}
}
