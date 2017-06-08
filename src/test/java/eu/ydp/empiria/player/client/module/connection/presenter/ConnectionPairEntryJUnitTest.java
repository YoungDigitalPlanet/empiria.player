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

package eu.ydp.empiria.player.client.module.connection.presenter;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("PMD")
public class ConnectionPairEntryJUnitTest {

    @Test
    public void equalsTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>(target, source);
        assertTrue(entry.equals(entry2));
        assertTrue(entry.equals(entry));
        assertTrue(entry2.equals(entry));

    }

    @Test
    public void notEqualsTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>("xxx", source);
        assertFalse(entry.equals(entry2));
        assertFalse(entry.equals(null));
    }

    @Test
    public void hashcodeCorrectTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>(target, source);

        assertEquals(entry.hashCode(), entry2.hashCode());
    }

    @Test
    public void hashcodeNotCorrectTest() {
        String target = "target";
        String source = "source";
        ConnectionPairEntry<String, String> entry = new ConnectionPairEntry<String, String>(source, target);
        ConnectionPairEntry<String, String> entry2 = new ConnectionPairEntry<String, String>("xxx", source);

        assertFalse(entry.hashCode() == entry2.hashCode());
    }

}
