/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
