package eu.ydp.empiria.player.client.module.bookmark;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnitParamsRunner.class)
public class BookmarkingHelperJUnitTest {

    public Object[] params() {
        return $(
                $("aaaaaaaa", "aaaaaaaa"),
                $("1234 6789 1234 6789 1234 6789 ", "1234 6789 1234 6789 1234 6789 "),
                $("1234 6789 1234 6789 1234 6789 ABCD", "1234 6789 1234 6789 1234..."),
                $("123456789012345678901234567890", "123456789012345678901234567890"),
                $("123456789012345678901234567890ABCD", "123456789012345678901234567...")
        );
    }

    @Test
    @Parameters(method = "params")
    public void getDefaultBookmarkTitle(String in, String out) {
        String result = BookmarkingHelper.getDefaultBookmarkTitle(in);
        assertThat(result, is(out));
    }
}
