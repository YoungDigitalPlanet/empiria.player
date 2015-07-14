package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResponseBeanConverterTest {

    private ResponseBeanConverter testObj;

    @Before
    public void before() {
        testObj = new ResponseBeanConverter();
    }

    @Test
    public void convertTest() {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setCardinality(Cardinality.ORDERED);
        responseBean.setCheckMode(CheckMode.EXPRESSION);
        String identifier = "identifier";
        responseBean.setIdentifier(identifier);

        CorrectResponseBean correctResponseBean = new CorrectResponseBean();
        responseBean.setCorrectResponse(correctResponseBean);

        String value0 = "value0";
        String value1 = "value1";
        List<ValueBean> values = Lists.newArrayList(getValueBean(value0), getValueBean(value1, "forIndex", "group", "groupMode"));
        correctResponseBean.setValues(values);

        Response response = testObj.convert(responseBean);

        assertEquals(Cardinality.ORDERED, response.cardinality);
        assertEquals(CheckMode.EXPRESSION, response.getCheckMode());
        assertEquals(identifier, response.identifier);
        CorrectAnswers correctAnswers = response.correctAnswers;
        assertEquals(2, correctAnswers.getAnswersCount());
        assertEquals(value0, correctAnswers.getResponseValue(0).getAnswers().get(0));
        assertEquals(value1, correctAnswers.getResponseValue(1).getAnswers().get(0));
        assertEquals("group", response.groups.get(0));

    }

    private ValueBean getValueBean(String value) {
        ValueBean valueBean = new ValueBean();
        valueBean.setValue(value);
        return valueBean;
    }

    private ValueBean getValueBean(String value, String forIndex, String group, String groupMode) {
        ValueBean valueBean = getValueBean(value);
        valueBean.setForIndex(forIndex);
        valueBean.setGroup(group);
        valueBean.setGroupMode(groupMode);
        return valueBean;
    }

}
