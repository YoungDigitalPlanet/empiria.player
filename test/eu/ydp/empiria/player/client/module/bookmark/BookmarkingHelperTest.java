package eu.ydp.empiria.player.client.module.bookmark;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.gwtutil.junit.runners.ParameterizedMethodsRunner;
import eu.ydp.gwtutil.junit.runners.ParameterizedMethodsRunner.MethodParameters;

@RunWith(ParameterizedMethodsRunner.class)
public class BookmarkingHelperTest {

	@MethodParameters(forMethod="getDefaultBookmarkTitle")
	public static List<String[]> getParams(){
		return Arrays.asList(new String[][]{
				{"aaaaaaaa", "aaaaaaaa"},
				{"1234 6789 1234 6789 1234 6789 ", "1234 6789 1234 6789 1234 6789 "},
				{"1234 6789 1234 6789 1234 6789 ABCD", "1234 6789 1234 6789 1234..."},
				{"123456789012345678901234567890", "123456789012345678901234567890"},
				{"123456789012345678901234567890ABCD", "123456789012345678901234567..."}
				});
	}
	
	@Test
	public void getDefaultBookmarkTitle(String in, String out){
		String result = BookmarkingHelper.getDefaultBookmarkTitle(in);
		assertThat(result, is(out));
	}
}
