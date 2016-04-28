package gwt.material.design.viki.client.ui;

/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.dnd.MaterialDnd;
import gwt.material.design.addins.client.dnd.constants.Restriction;
import gwt.material.design.addins.client.dnd.events.DragEndEvent;
import gwt.material.design.addins.client.dnd.events.DragMoveEvent;
import gwt.material.design.addins.client.dnd.events.DragStartEvent;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.html.Div;

public class VikiScrollPanel extends MaterialPanel {
    private Div contentPanel = new Div();
    private Div scrollBar = new Div();
    private Restriction rest;
    private MaterialDnd dndEvent;
    private double diff;
    private Widget logChild;
//    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("NameOfYourLogger");

    public VikiScrollPanel() {
        super.add(contentPanel);
        super.add(scrollBar);
        stylize();
        draggableConfig();
    }

    private void draggableConfig() {
        dndEvent = new MaterialDnd();
        dndEvent.setInertia(true);
        rest = new Restriction();
        rest.setEndOnly(false);
        rest.setBottom(0);
        dndEvent.setRestriction(rest);
        dndEvent.addDragEndHandler(new DragEndEvent.DragEndHandler() {
            @Override
            public void onDragEnd(DragEndEvent event) {
                scrollBar.setVisibility(Style.Visibility.HIDDEN);
            }
        });
        dndEvent.addDragStartHandler(new DragStartEvent.DragStartHandler() {
            @Override
            public void onDragStart(DragStartEvent event) {
                if (diff > 0) {
                    scrollBar.setVisibility(Style.Visibility.VISIBLE);
                }
            }
        });
        dndEvent.addDragMoveHandler(new DragMoveEvent.DragMoveHandler() {
            @Override
            public void onDragMove(DragMoveEvent event) {
                double topOffset = getAbsoluteTop() - contentPanel.getAbsoluteTop();
                double c = topOffset / contentPanel.getOffsetHeight();
                double top = c * getOffsetHeight();
                scrollBar.setTop(top);
            }
        });
    }

    private void stylize() {
        setStyleName("viki-scroll-panel");
        contentPanel.setStyleName("content-panel");
        scrollBar.setStyleName("scroll-bar");
        scrollBar.setVisibility(Style.Visibility.HIDDEN);
    }

    @Override
    public void add(Widget child) {
        if (contentPanel.getElement().getChildCount() == 0) {
            logChild = child;
        }
        contentPanel.add(child);
        double myOffsetHeight = getOffsetHeight();
        double contentOffsetHeight = contentPanel.getOffsetHeight();
        double m = 10000D;
        diff = (contentPanel.getOffsetHeight() - myOffsetHeight) * m;
        Double coefficient;
        if (diff > 0) {
            coefficient = diff / contentOffsetHeight;
            rest.setTop(coefficient / m);
            rest.setBottom(1 - coefficient / m);
            scrollBar.setHeight("" + myOffsetHeight / contentOffsetHeight * myOffsetHeight + "px");
            ((VikiPurchasePosition) logChild).setPrice(0);
        } else {
            coefficient = -diff / contentOffsetHeight + m;
            rest.setBottom(coefficient / m);
            rest.setTop(0);
        }
        int childCount = contentPanel.getElement().getChildCount();
        switch (childCount % 2) {
            case 0:
                ((VikiPurchasePosition) logChild).setProduct("myOffsetHeight = " + myOffsetHeight);
                break;
            case 1:
                ((VikiPurchasePosition) logChild).setProduct("contentOffsetHeight = " + contentOffsetHeight);
                break;
//            case 2:
//                ((VikiPurchasePosition) logChild).setProduct("coefficient = " + coefficient);
//                break;
        }
        dndEvent.setTarget(contentPanel);
    }
}
