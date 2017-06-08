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

package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.gwtutil.client.StringUtils;
import org.junit.Test;

import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.ITEM;
import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.TEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContentFieldInfoTest {

    @Test
    public void creatingFieldType() {
        assertThat(FieldType.getType("test.title"), is(equalTo(FieldType.TEST)));
        assertThat(FieldType.getType("item.todo"), is(equalTo(FieldType.ITEM)));
        assertThat(FieldType.getType("todo"), is(equalTo(FieldType.UNKNOWN)));
    }

    @Test
    public void shouldCreateTestFieldInfo() {
        ContentFieldTestData[] infos = new ContentFieldTestData[]{
                new ContentFieldTestData("test", StringUtils.EMPTY_STRING, "$[test]", "\\$\\[test]", TEST),
                new ContentFieldTestData("test.done", VariableName.DONE.toString(), "$[test.done]", "\\$\\[test.done]", TEST),
                new ContentFieldTestData("test.todo", VariableName.TODO.toString(), "$[test.todo]", "\\$\\[test.todo]", TEST),
                new ContentFieldTestData("test.checks", FlowActivityVariablesProcessor.CHECKS, "$[test.checks]", "\\$\\[test.checks]", TEST),
                new ContentFieldTestData("test.mistakes", VariableName.MISTAKES.toString(), "$[test.mistakes]", "\\$\\[test.mistakes]", TEST),
                new ContentFieldTestData("test.show_answers", FlowActivityVariablesProcessor.SHOW_ANSWERS, "$[test.show_answers]", "\\$\\[test.show_answers]",
                        TEST),
                new ContentFieldTestData("test.reset", FlowActivityVariablesProcessor.RESET, "$[test.reset]", "\\$\\[test.reset]", TEST),
                new ContentFieldTestData("test.result", "RESULT", "$[test.result]", "\\$\\[test.result]", TEST),
                new ContentFieldTestData("item.done", VariableName.DONE.toString(), "$[item.done]", "\\$\\[item.done]", ITEM),
                new ContentFieldTestData("item.todo", VariableName.TODO.toString(), "$[item.todo]", "\\$\\[item.todo]", ITEM),
                new ContentFieldTestData("item.checks", FlowActivityVariablesProcessor.CHECKS, "$[item.checks]", "\\$\\[item.checks]", ITEM),
                new ContentFieldTestData("item.mistakes", VariableName.MISTAKES.toString(), "$[item.mistakes]", "\\$\\[item.mistakes]", ITEM),
                new ContentFieldTestData("item.show_answers", FlowActivityVariablesProcessor.SHOW_ANSWERS, "$[item.show_answers]", "\\$\\[item.show_answers]",
                        ITEM), new ContentFieldTestData("item.reset", FlowActivityVariablesProcessor.RESET, "$[item.reset]", "\\$\\[item.reset]", ITEM),
                new ContentFieldTestData("item.result", "RESULT", "$[item.result]", "\\$\\[item.result]", ITEM),
                new ContentFieldTestData("item.page_num", "PAGE_NUM", "$[item.page_num]", "\\$\\[item.page_num]", ITEM),
                new ContentFieldTestData("item.page_count", "PAGE_COUNT", "$[item.page_count]", "\\$\\[item.page_count]", ITEM)};

        for (ContentFieldTestData infoTestData : infos) {
            ContentFieldInfo info = new ContentFieldInfoFactory().create(infoTestData.getTagName(), null);

            assertThat(infoTestData.getTagName(), info.getType(), is(equalTo(infoTestData.getType())));
            assertThat(infoTestData.getTagName(), info.getTag(), is(equalTo(infoTestData.getTag())));
            assertThat(infoTestData.getTagName(), info.getValueName(), is(equalTo(infoTestData.getValueName())));
            assertThat(infoTestData.getTagName(), info.getPattern(), is(equalTo(infoTestData.getPattern())));
        }

    }

    @Test
    public void shouldReturnValueWhen_hasHandler() {
        ContentFieldInfo info = new ContentFieldInfoFactory().create("", new FieldValueHandler() {

            @Override
            public String getValue(ContentFieldInfo info, int refItemIndex) {
                return info.getType() + String.valueOf(refItemIndex);
            }
        });

        assertThat(info.getValue(1), is(equalTo("UNKNOWN1")));
    }

    @Test
    public void shouldReturnDefaultValueWhen_hasNoHandler() {
        ContentFieldInfo info = new ContentFieldInfo();
        assertThat(info.getValue(0), is(equalTo(StringUtils.EMPTY_STRING)));
    }

    private class ContentFieldTestData {

        private final String tagName;
        private final String variableName;
        private final String tag;
        private final String pattern;
        private final FieldType type;

        public ContentFieldTestData(String tagName, String variableName, String tag, String pattern, FieldType type) {
            this.tagName = tagName;
            this.variableName = variableName;
            this.tag = tag;
            this.pattern = pattern;
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public String getTagName() {
            return tagName;
        }

        public String getPattern() {
            return pattern;
        }

        public String getValueName() {
            return variableName;
        }

        public FieldType getType() {
            return type;
        }

    }

}
