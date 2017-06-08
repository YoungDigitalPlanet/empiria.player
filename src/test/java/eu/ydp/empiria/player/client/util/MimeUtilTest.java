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

package eu.ydp.empiria.player.client.util;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class MimeUtilTest {

    private final MimeUtil testObj = new MimeUtil();

    Object[] data() {
        return $($("test.mp3", "audio/mp4"), $("test.ogg", "audio/ogg"), $("test.ogv", "audio/ogg"), $("test.ukn", ""));
    }

    @Test
    @Parameters(method = "data")
    public void shouldReturnValidMimeFromFileExtension(String file, String mime) {
        // when
        String result = testObj.getMimeTypeFromExtension(file);

        // then
        assertEquals(result, mime);
    }
}
