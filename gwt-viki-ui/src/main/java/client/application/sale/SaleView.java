package client.application.sale;

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


import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

import client.VoidAsyncCallback;
import client.rpc.ServerEventBusService;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimator;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.viki.client.ui.VikiPurchasePosition;
import gwt.material.design.viki.client.ui.VikiScrollPanel;
import gwt.material.design.viki.client.ui.VikiTile;
import shared.AddPositionToPurchaseEvent;
import shared.ChangePurchasePositionEvent;
import shared.Product;

public class SaleView extends ViewImpl implements SalePresenter.MyView {
    @UiField
    VikiScrollPanel purchase;
    @UiField
    Div right_child;
    @UiField
    Div tiles;
    @UiField
    VikiPurchasePosition summary;
    @UiField
    Div emptyPurchaseWrapper;
    @UiField
    Div purchaseWrapper;
    @UiField
    Div emptyTiles;
    private int order = 1;
    private Map<String, VikiPurchasePosition> cache = new HashMap<>();

    @Inject
    SaleView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addPositionToPurchase(AddPositionToPurchaseEvent anEvent) {
        showPurchase();
        final VikiPurchasePosition position = new VikiPurchasePosition();
        position.setProduct(anEvent.getName());
        position.setCount(anEvent.getCount());
        position.setPrice(anEvent.getPrice());
        position.setFlexOrder(++order);
        position.setOpacity(0);
        MaterialAnimator.animate(Transition.FADEIN, position, 0, 300, new Runnable() {
            @Override
            public void run() {
                position.setOpacity(1);
            }
        });
        purchase.add(position);
//        scrollPanel.refresh();
        cache.put(anEvent.getItem(), position);
    }

    private void showPurchase() {
        if (!purchaseWrapper.isVisible()) {
            emptyPurchaseWrapper.setVisible(false);
            purchaseWrapper.setVisible(true);
        }
    }

    @Override
    public void changePosition(ChangePurchasePositionEvent anEvent) {
        VikiPurchasePosition widget = cache.get(anEvent.getItem());
        widget.setCount(anEvent.getCount());
        widget.setPrice(anEvent.getPrice());
        widget.setFlexOrder(++order);
    }

    @Override
    public void updateSum(Long purchaseSum) {
        summary.setPrice(purchaseSum);
    }

    @Override
    public void setEmpty(boolean empty) {
        emptyTiles.setVisible(empty);
        tiles.setVisible(!empty);
    }

    @Override
    public void clearPurchase() {
        purchaseWrapper.setVisible(false);
        emptyPurchaseWrapper.setVisible(true);
        cache.clear();
        purchase.clear();
        order = 1;
//        ServerEventBusService.App.getInstance().clearPurchase(new VoidAsyncCallback());
        MaterialToast.fireToast("Чек аннулирован");
        ;
    }

    @Override
    protected void initWidget(IsWidget widget) {
        super.initWidget(widget);
//
//        MaterialDnd dndEndOnly = new MaterialDnd();
//        dndEndOnly.setTarget(endOnlyPanel);
//
//        Restriction restriction = new Restriction();
//        restriction.setEndOnly(false);
//        dndEndOnly.setRestriction(restriction);

        summary.setWaves(null);
        cache.clear();
        order = 1;
        purchaseWrapper.setVisible(false);
        ServerEventBusService.App.getInstance().getTiles(new AsyncCallback<Map<Integer, Product>>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(Map<Integer, Product> result) {
                tiles.clear();
                for (int i = 0; i < 11; i++) {
                    VikiTile tile = new VikiTile();
                    if (result.containsKey(i)) {
                        Product product = result.get(i);
                        tile.setItem(product.getItem());
                        tile.setProduct(product.getProduct());
                        tile.addClickHandler(new ClickHandler() {
                            @Override
                            public void onClick(ClickEvent event) {
                                Object element = event.getSource();
                                if (element instanceof VikiTile) {
                                    eventTileClicked(((VikiTile) element).getItem());
                                }
                            }
                        });
                    }
                    tiles.add(tile);
                }
            }
        });
    }

    public interface Binder extends UiBinder<Widget, SaleView> {
    }

    @UiHandler("payButton")
    public void onPay(ClickEvent event) {
//        purchaseWrapper.setVisible(false);
//        emptyPurchaseWrapper.setVisible(true);
//        cache.clear();
//        purchase.clear();
//        order = 1;
        ServerEventBusService.App.getInstance().onSubTotal(new VoidAsyncCallback());
//        MaterialToast.fireToast("Чек аннулирован");
//        modal.openModal();
    }

    private void eventTileClicked(String item) {
        ServerEventBusService.App.getInstance().onTileClickEvent(item, new VoidAsyncCallback());
    }
}
