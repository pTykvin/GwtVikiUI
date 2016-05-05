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


import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLabel;

public class VikiTile extends MaterialWidget implements HasClickHandlers {

    MaterialLabel shortName = new MaterialLabel();
    MaterialLabel name = new MaterialLabel();

    String item;

    String product;

    public VikiTile() {
        super(Document.get().createDivElement());
        stylize();
        construct();
    }

    public void construct() {
        setWaves(WavesType.DEFAULT);
        add(shortName);
        add(name);
    }

    public void stylize() {
        setStyleName("viki-tile");
        setShadow(1);
        shortName.setStyleName("short-name");
        name.setStyleName("name");
        setVisibility(Style.Visibility.HIDDEN);
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
        boolean hasName = product != null && product.length() > 2;
        if (hasName) {
            shortName.setText(product.substring(0, 2));
            name.setText(product);
        }
        setVisibility(hasName ? Style.Visibility.VISIBLE : Style.Visibility.HIDDEN);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return addDomHandler(handler, ClickEvent.getType());
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
