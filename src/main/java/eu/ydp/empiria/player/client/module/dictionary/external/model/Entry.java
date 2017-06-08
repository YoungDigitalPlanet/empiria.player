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

package eu.ydp.empiria.player.client.module.dictionary.external.model;

public class Entry {

    private final String entry;
    private final String entryDescription;
    private final String type;
    private final String entryExample;
    private final String entrySound;
    private final String entryExampleSound;
    private final String label;

    Entry(String entry, String entryDescription, String type, String entryExample, String entrySound, String entryExampleSound, String label) {
        this.entry = entry;
        this.entryDescription = entryDescription;
        this.type = type;
        this.entryExample = entryExample;
        this.entrySound = entrySound;
        this.entryExampleSound = entryExampleSound;
        this.label = label;
    }

    public String getEntry() {
        return entry;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public String getType() {
        return type;
    }

    public String getEntryExample() {
        return entryExample;
    }

    public String getEntrySound() {
        return entrySound;
    }

    public String getEntryExampleSound() {
        return entryExampleSound;
    }

    public String getLabel() {
        return label;
    }
}
