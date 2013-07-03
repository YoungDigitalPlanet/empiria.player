package eu.ydp.empiria.player.client.module.gap;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;


public class GapExpressionReplacerJUnitTest {

	private GapExpressionReplacer replacer;
	
	@Before
	public void setUp() {
		replacer = Guice.createInjector().getInstance(GapExpressionReplacer.class);
	}
	
	@Test
	public void ensureReplacement(){
		// given
		final String ORIGINAL_TEXT = "a";
		replacer.useCharacters("a|b|c|dx");
		
		// when
		String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);
		
		// then
		assertThat(replacedText).isEqualTo("b");
	}
	
	@Test
	public void ensureReplacement_notEligibleForReplacement(){
		// given
		final String ORIGINAL_TEXT = "e";
		replacer.useCharacters("a|b|c|dx");
		
		// when
		String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);
		
		// then
		assertThat(replacedText).isEqualTo("e");
	}
	
	@Test
	public void ensureReplacement_notEligibleForReplacement_partIsInSet(){
		// given
		final String ORIGINAL_TEXT = "af";
		replacer.useCharacters("a|b|c|dx");
		
		// when
		String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);
		
		// then
		assertThat(replacedText).isEqualTo("af");
	}
	
	@Test
	public void ensureReplacement_empty(){
		// given
		final String ORIGINAL_TEXT = "";
		replacer.useCharacters("a|b|c|dx");
		
		// when
		String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);
		
		// then
		assertThat(replacedText).isEqualTo("");
	}
	
	@Test
	public void ensureReplacement_charactersNotSet(){
		// given
		final String ORIGINAL_TEXT = "a";
		
		// when
		String replacedText = replacer.ensureReplacement(ORIGINAL_TEXT);
		
		// then
		assertThat(replacedText).isEqualTo("a");
	}
}
