package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VariableInterpreterJUnitTest {

    @InjectMocks
    private VariableInterpreter testObj;
    @Mock
    private ContentFieldRegistry contentFieldRegistry;
    @Mock
    private ContentFieldInfo contentFieldInfo;
    @Mock
    private InfoModuleContentTokenizer.Token firstToken;
    @Mock
    private InfoModuleContentTokenizer.Token secondToken;
    private String firstTokenName = "first";
    private String secondTokenName = "second";

    @Before
    public void init(){
        when(firstToken.getName()).thenReturn(firstTokenName);
        when(secondToken.getName()).thenReturn(secondTokenName);
        when(contentFieldRegistry.getFieldInfo(firstTokenName)).thenReturn(Optional.of(contentFieldInfo));
    }

    @Test
    public void shouldReturnTokenName_whenIsNotFieldInfo() {
        // given
        when(firstToken.isFieldInfo()).thenReturn(false);
        List<InfoModuleContentTokenizer.Token> tokens = Lists.newArrayList(firstToken);

        // when
        String result = testObj.replaceAllTags(tokens, 0);

        // then
        assertThat(result).isEqualTo(firstTokenName);
    }

    @Test
    public void shouldReturnConnectedTokenNames_whenAreNotFieldInfo() {
        // given
        String expectedString = "firstsecond";
        when(firstToken.isFieldInfo()).thenReturn(false);
        when(secondToken.isFieldInfo()).thenReturn(false);

        List<InfoModuleContentTokenizer.Token> tokens = Lists.newArrayList(firstToken, secondToken);

        // when
        String result = testObj.replaceAllTags(tokens, 0);

        // then
        assertThat(result).isEqualTo(expectedString);
    }

    @Test
    public void shouldReturnContentFieldValue_whenIsFieldInfo(){
        // given
        int itemIndex = 5;
        String contentFieldValue = "contentValue";
        when(contentFieldInfo.getValue(itemIndex)).thenReturn(contentFieldValue);
        when(firstToken.isFieldInfo()).thenReturn(true);
        List<InfoModuleContentTokenizer.Token> tokens = Lists.newArrayList(firstToken);

        // when
        String result = testObj.replaceAllTags(tokens, itemIndex);

        //then
        assertThat(result).isEqualTo(contentFieldValue);
    }

    @Test
    public void shouldReturnConectedContentFieldValue_andTokenName_whenFirstIsFieldInfo_andSecondIsNot(){
        // given
        int itemIndex = 5;
        String contentFieldValue = "contentValue";
        String expectedValue = "contentValuesecond";
        when(contentFieldInfo.getValue(itemIndex)).thenReturn(contentFieldValue);
        when(firstToken.isFieldInfo()).thenReturn(true);
        when(secondToken.isFieldInfo()).thenReturn(false);
        List<InfoModuleContentTokenizer.Token> tokens = Lists.newArrayList(firstToken, secondToken);

        // when
        String result = testObj.replaceAllTags(tokens, itemIndex);

        //then
        assertThat(result).isEqualTo(expectedValue);
    }
}
