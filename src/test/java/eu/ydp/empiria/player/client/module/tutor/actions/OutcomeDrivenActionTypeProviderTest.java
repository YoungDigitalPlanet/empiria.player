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

package eu.ydp.empiria.player.client.module.tutor.actions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeDrivenActionTypeProviderTest {

    @InjectMocks
    OutcomeDrivenActionTypeProvider actionTypeProvider;

    @Mock
    OnOkAction okAction;
    @Mock
    OnWrongAction wrongAction;
    @Mock
    OnPageAllOkAction pageAllOkAction;

    @Test
    public void shouldReturnPageAllOkActionPriorToOnOkAction() {
        // when
        Set<OutcomeDrivenAction> actions = actionTypeProvider.getActions();

        // then
        List<OutcomeDrivenAction> list = new ArrayList<OutcomeDrivenAction>(actions);
        int indexOfPageAllOk = list.indexOf(pageAllOkAction);
        int indexOfOk = list.indexOf(okAction);
        assertThat(indexOfPageAllOk, is(lessThan(indexOfOk)));
    }
}
