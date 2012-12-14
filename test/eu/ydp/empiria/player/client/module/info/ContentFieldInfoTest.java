package eu.ydp.empiria.player.client.module.info;

import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.ITEM;
import static eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType.TEST;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.gwtutil.client.StringUtils;


public class ContentFieldInfoTest {

	@Test
	public void creatingFieldType(){
		assertThat(FieldType.getType("test.title"), is(equalTo(FieldType.TEST)));
		assertThat(FieldType.getType("item.todo"), is(equalTo(FieldType.ITEM)));
		assertThat(FieldType.getType("todo"), is(equalTo(FieldType.UNKNOWN)));
	}
	
	@Test
	public void shouldCreateTestFieldInfo(){
		ContentFieldTestData[] infos = new ContentFieldTestData[]{
				new ContentFieldTestData("test", StringUtils.EMPTY_STRING, "$[test]", "\\$\\[test]", TEST),
				new ContentFieldTestData("test.done", DefaultVariableProcessor.DONE, "$[test.done]", "\\$\\[test.done]", TEST),
				new ContentFieldTestData("test.todo", DefaultVariableProcessor.TODO, "$[test.todo]", "\\$\\[test.todo]", TEST),
				new ContentFieldTestData("test.checks", DefaultVariableProcessor.CHECKS, "$[test.checks]", "\\$\\[test.checks]", TEST),
				new ContentFieldTestData("test.mistakes", DefaultVariableProcessor.MISTAKES, "$[test.mistakes]", "\\$\\[test.mistakes]", TEST),
				new ContentFieldTestData("test.show_answers", DefaultVariableProcessor.SHOW_ANSWERS, "$[test.show_answers]", "\\$\\[test.show_answers]", TEST),
				new ContentFieldTestData("test.reset", DefaultVariableProcessor.RESET, "$[test.reset]", "\\$\\[test.reset]", TEST),
				new ContentFieldTestData("test.result", "RESULT", "$[test.result]", "\\$\\[test.result]", TEST),
				new ContentFieldTestData("item.done", DefaultVariableProcessor.DONE, "$[item.done]", "\\$\\[item.done]", ITEM),
				new ContentFieldTestData("item.todo", DefaultVariableProcessor.TODO, "$[item.todo]", "\\$\\[item.todo]", ITEM),
				new ContentFieldTestData("item.checks", DefaultVariableProcessor.CHECKS, "$[item.checks]", "\\$\\[item.checks]", ITEM),
				new ContentFieldTestData("item.mistakes", DefaultVariableProcessor.MISTAKES, "$[item.mistakes]", "\\$\\[item.mistakes]", ITEM),
				new ContentFieldTestData("item.show_answers", DefaultVariableProcessor.SHOW_ANSWERS, "$[item.show_answers]", "\\$\\[item.show_answers]", ITEM),
				new ContentFieldTestData("item.reset", DefaultVariableProcessor.RESET, "$[item.reset]", "\\$\\[item.reset]", ITEM),
				new ContentFieldTestData("item.result", "RESULT", "$[item.result]", "\\$\\[item.result]", ITEM),
				new ContentFieldTestData("item.page_num", "PAGE_NUM", "$[item.page_num]", "\\$\\[item.page_num]", ITEM),
				new ContentFieldTestData("item.page_count", "PAGE_COUNT", "$[item.page_count]", "\\$\\[item.page_count]", ITEM)
		};
		
		for(ContentFieldTestData infoTestData: infos){
			ContentFieldInfo info = new ContentFieldInfo().setTagName(infoTestData.getTagName());
			
			assertThat(infoTestData.getTagName(), info.getType(), is(equalTo(infoTestData.getType())));
			assertThat(infoTestData.getTagName(), info.getTag(), is(equalTo(infoTestData.getTag())));
			assertThat(infoTestData.getTagName(), info.getValueName(), is(equalTo(infoTestData.getValueName())));
			assertThat(infoTestData.getTagName(), info.getPattern(), is(equalTo(infoTestData.getPattern())));
		}		
		
	}
	
	@Test
	public void shouldReturnValueWhen_hasHandler(){
		ContentFieldInfo info = new ContentFieldInfo();
		info.setTagName("");
		
		info.setHandler(new FieldValueHandler() {
			
			@Override
			public String getValue(ContentFieldInfo info, int refItemIndex) {
				System.out.println("ind: " + refItemIndex);
				return info.getType() + String.valueOf(refItemIndex);
			}
		});
		
		assertThat(info.getValue(1), is(equalTo("UNKNOWN1")));
	}
	
	@Test
	public void shouldReturnDefaultValueWhen_hasNoHandler(){
		ContentFieldInfo info = new ContentFieldInfo();
		assertThat(info.getValue(0), is(equalTo(StringUtils.EMPTY_STRING)));
	}
	
	private class ContentFieldTestData{
		
		private String tagName;
		private String variableName;
		private String tag;
		private String pattern;
		private FieldType type;

		public ContentFieldTestData(String tagName, String variableName, String tag, String pattern, FieldType type){
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
