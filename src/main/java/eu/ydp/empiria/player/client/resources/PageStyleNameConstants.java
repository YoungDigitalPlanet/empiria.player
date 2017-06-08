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

package eu.ydp.empiria.player.client.resources;

import com.google.gwt.i18n.client.Constants;

public interface PageStyleNameConstants extends Constants {

    @Constants.DefaultStringValue("qp-page")
    String QP_PAGE();

    @Constants.DefaultStringValue("qp-page-content")
    String QP_PAGE_CONTENT();

    @Constants.DefaultStringValue("qp-page-error")
    String QP_PAGE_ERROR();

    @Constants.DefaultStringValue("qp-page-error-text")
    String QP_PAGE_ERROR_TEXT();

    @Constants.DefaultStringValue("qp-page-in-page")
    String QP_PAGE_IN_PAGE();

    @Constants.DefaultStringValue("qp-page-item")
    String QP_PAGE_ITEM();

    @Constants.DefaultStringValue("qp-page-next")
    String QP_PAGE_NEXT();

    @Constants.DefaultStringValue("qp-page-preloader")
    String QP_PAGE_PRELOADER();

    @Constants.DefaultStringValue("qp-page-prev")
    String QP_PAGE_PREV();

    @Constants.DefaultStringValue("qp-page-selected")
    String QP_PAGE_SELECTED();

    @Constants.DefaultStringValue("qp-page-title")
    String QP_PAGE_TITLE();

    @Constants.DefaultStringValue("qp-page-unselected")
    String QP_PAGE_UNSELECTED();

    @DefaultStringValue("qp-footer")
    String QP_FOOTER();

    @DefaultStringValue("qp-header")
    String QP_HEADER();

    @DefaultStringValue("qp-body")
    String QP_BODY();

    @DefaultStringValue("qp-page-template")
    String QP_PAGE_TEMPLATE();
}
