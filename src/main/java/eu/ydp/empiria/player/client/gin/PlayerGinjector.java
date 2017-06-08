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

package eu.ydp.empiria.player.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import eu.ydp.empiria.player.client.PlayerLoader;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.module.*;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorGinModule;
import eu.ydp.empiria.player.client.util.events.external.ExternalEventDispatcher;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.gin.module.UtilGinModule;

@GinModules(value = {PlayerGinModule.class, UtilGinModule.class, ChoiceGinModule.class, ConnectionGinModule.class, SourceListGinModule.class,
        SelectionGinModule.class, SimulationGinModule.class, SlideshowGinModule.class,
        OrderingGinModule.class, ColorfillGinModule.class, DragDropGinModule.class, TutorGinModule.class, ButtonGinModule.class,
        DrawingGinModule.class, BonusGinModule.class, ProgressBonusGinModule.class, VideoGinModule.class, DictionaryGinModule.class,
        TextEditorGinModule.class, TestGinModule.class, SpeechScoreGinModule.class,
        ExternalGinModule.class, PicturePlayerGinModule.class, MathJaxGinModule.class, AccordionGinModule.class,
        PageContentGinModule.class, MathGinModule.class, FeedbackGinModule.class, StyleGinModule.class,
        MediaGinModule.class,
        ReportGinModule.class,
        IdentificationGinModule.class
})
public interface PlayerGinjector extends Ginjector {

    DeliveryEngine getDeliveryEngine();

    EventsBus getEventsBus();

    Page getPage();

    PanelCache getPanelCache();

    TextTrackFactory getTextTrackFactory();

    ExternalEventDispatcher getEventDispatcher();

    PlayerLoader getPlayerLoader();
}
