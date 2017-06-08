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

package eu.ydp.empiria.player.client.controller.flow.processing.commands;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public abstract class FlowCommand implements IFlowCommand {

    protected String name;

    private FlowCommand(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static IFlowCommand fromJsObject(JavaScriptObject commandJsObject) {
        String objName = getJsObjectName(commandJsObject);
        if (objName == null || objName.equals("")) {
            return null;
        }
        int objIndex = getJsObjectIndex(commandJsObject);
        String groupIdentifierString = getJsObjectGroupIdentifier(commandJsObject);
        GroupIdentifier groupIdentifier = new DefaultGroupIdentifier(groupIdentifierString);

        IFlowCommand command = null;

        if (NavigateNextItem.NAME.equals(objName)) {
            command = new NavigateNextItem();
        } else if (NavigatePreviousItem.NAME.equals(objName)) {
            command = new NavigatePreviousItem();
        } else if (NavigateFirstItem.NAME.equals(objName)) {
            command = new NavigateFirstItem();
        } else if (NavigateLastItem.NAME.equals(objName)) {
            command = new NavigateLastItem();
        } else if (NavigateToc.NAME.equals(objName)) {
            command = new NavigateToc();
        } else if (NavigateTest.NAME.equals(objName)) {
            command = new NavigateTest();
        } else if (NavigateSummary.NAME.equals(objName)) {
            command = new NavigateSummary();
        } else if (NavigateGotoItem.NAME.equals(objName)) {
            command = new NavigateGotoItem(objIndex);
        } else if (NavigatePreviewItem.NAME.equals(objName)) {
            command = new NavigatePreviewItem(objIndex);
        } else if (Continue.NAME.equals(objName)) {
            command = new Continue(groupIdentifier);
        } else if (Check.NAME.equals(objName)) {
            command = new Check(groupIdentifier);
        } else if (Reset.NAME.equals(objName)) {
            command = new Reset(groupIdentifier);
        } else if (ShowAnswers.NAME.equals(objName)) {
            command = new ShowAnswers(groupIdentifier);
        } else if (Lock.NAME.equals(objName)) {
            command = new Lock(groupIdentifier);
        } else if (Unlock.NAME.equals(objName)) {
            command = new Unlock(groupIdentifier);
        }

        return command;
    }

    private static native String getJsObjectName(JavaScriptObject obj)/*-{
        if (typeof obj.name == 'string') {
            return obj.name;
        }
        return "";
    }-*/;

    private static native int getJsObjectIndex(JavaScriptObject obj)/*-{
        if (typeof obj.index == 'number') {
            return obj.index;
        }
        return -1;
    }-*/;

    private static native String getJsObjectGroupIdentifier(JavaScriptObject obj)/*-{
        if (typeof obj.groupIdentifier == 'string') {
            return obj.groupIdentifier;
        }
        return "";
    }-*/;

    public final static class NavigateNextItem extends FlowCommand {
        public NavigateNextItem() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_NEXT_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.nextPage();
        }
    }

    public static final class NavigatePreviousItem extends FlowCommand {
        public NavigatePreviousItem() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_PREVIOUS_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.previousPage();
        }
    }

    public static final class NavigateFirstItem extends FlowCommand {
        public NavigateFirstItem() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_FIRST_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoFirstPage();
        }
    }

    public static final class NavigateLastItem extends FlowCommand {
        public NavigateLastItem() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_LAST_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoLastPage();
        }
    }

    public static final class NavigateToc extends FlowCommand {
        public NavigateToc() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_TOC";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoToc();
        }
    }

    public static final class NavigateTest extends FlowCommand {
        public NavigateTest() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_TEST";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoTest();
        }
    }

    public static final class NavigateSummary extends FlowCommand {
        public NavigateSummary() {
            super(NAME);
        }

        public static final String NAME = "NAVIGATE_SUMMARY";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoSummary();
        }
    }

    public abstract static class FlowCommandWithIndex extends FlowCommand {
        public FlowCommandWithIndex(String name, int index) {
            super(name);
            this.index = index;
        }

        protected int index;
    }

    public static final class NavigateGotoItem extends FlowCommandWithIndex {
        public NavigateGotoItem(int index) {
            super(NAME, index);
        }

        public static final String NAME = "NAVIGATE_GOTO_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.gotoPage(index);
        }
    }

    public static final class NavigatePreviewItem extends FlowCommandWithIndex {
        public NavigatePreviewItem(int index) {
            super(NAME, index);
        }

        public static final String NAME = "NAVIGATE_PREVIEW_ITEM";

        @Override
        public void execute(FlowCommandsListener listener) {
            listener.previewPage(index);
        }
    }

    public abstract static class FlowCommandForGroup extends FlowCommand {
        public FlowCommandForGroup(String name, GroupIdentifier groupIdentifier) {
            super(name);
            this.groupIdentifier = groupIdentifier;
        }

        protected GroupIdentifier groupIdentifier;
    }

    public static class Check extends FlowCommandForGroup {
        public Check(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public Check() {
            super(NAME, null);
        }

        public static final String NAME = "CHECK";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.checkPage();
            } else {
                listener.checkGroup(groupIdentifier);
            }
        }
    }

    public static class Continue extends FlowCommandForGroup {
        public Continue(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public Continue() {
            super(NAME, null);
        }

        public static final String NAME = "CONTINUE";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.continuePage();
            } else {
                listener.continueGroup(groupIdentifier);
            }

        }
    }

    public static class Reset extends FlowCommandForGroup {
        public Reset(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public Reset() {
            super(NAME, null);
        }

        public static final String NAME = "RESET";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.resetPage();
            } else {
                listener.resetGroup(groupIdentifier);
            }
        }
    }

    public static class ShowAnswers extends FlowCommandForGroup {
        public ShowAnswers(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public ShowAnswers() {
            super(NAME, null);
        }

        public static final String NAME = "SHOW_ANSWERS";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.showAnswersPage();
            } else {
                listener.showAnswersGroup(groupIdentifier);
            }
        }
    }

    public static class Lock extends FlowCommandForGroup {
        public Lock(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public Lock() {
            super(NAME, null);
        }

        public static final String NAME = "LOCK";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.lockPage();
            } else {
                listener.lockGroup(groupIdentifier);
            }
        }
    }

    public static class Unlock extends FlowCommandForGroup {
        public Unlock(GroupIdentifier groupIdentifier) {
            super(NAME, groupIdentifier);
        }

        public Unlock() {
            super(NAME, null);
        }

        public static final String NAME = "UNLOCK";

        @Override
        public void execute(FlowCommandsListener listener) {
            if (groupIdentifier == null || "".equals(groupIdentifier.getIdentifier())) {
                listener.unlockPage();
            } else {
                listener.unlockGroup(groupIdentifier);
            }
        }
    }

}
