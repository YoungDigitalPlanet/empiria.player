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

package eu.ydp.empiria.player.client.module.bonus;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyObject;
import eu.ydp.empiria.player.client.components.animation.swiffy.SwiffyService;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;

public class SwiffyBonus implements BonusWithAsset {

    private static final String JS_FILE_SUFFIX = ".js";
    @Inject
    private BonusPopupPresenter presenter;
    @Inject
    private EmpiriaPaths empiriaPaths;
    @Inject
    private SwiffyService swiffyService;

    private String name;
    private String url;
    private Size size;

    private final EndHandler endHandler = new EndHandler() {

        @Override
        public void onEnd() {
            swiffyService.clear(name);
        }
    };

    @Override
    public void execute() {
        SwiffyObject swiffyObject = swiffyService.getSwiffyObject(name, url);
        presenter.showAnimation(swiffyObject, size, endHandler);
    }

    @Override
    public void setAsset(String asset, Size size) {
        this.url = resolveUrl(asset);
        this.name = asset;
        this.size = size;
    }

    private String resolveUrl(String asset) {
        String jsFilename = asset + JS_FILE_SUFFIX;
        return empiriaPaths.getCommonsFilePath(jsFilename);
    }

}
