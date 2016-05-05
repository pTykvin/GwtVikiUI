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
import com.google.gwt.i18n.client.NumberFormat;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Display;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Label;

public class VikiPurchasePosition extends MaterialWidget {

    Label name = new Label();
    Label count = new Label();
    Label decimal = new Label();
    Label fraction = new Label();
    Div combineName = new Div();
    Div combinePrice = new Div();

    public VikiPurchasePosition() {
        super(Document.get().createDivElement());
        stylize();
        construct();
    }

    public void construct() {
        setWaves(WavesType.DEFAULT);
        combineName.add(name);
        combineName.add(count);
        combinePrice.add(decimal);
        combinePrice.add(fraction);
        add(combineName);
        add(combinePrice);
    }

    public void stylize() {
        setStyleName("viki-purchase-position");
        name.setStyleName("name");
        count.setStyleName("count");
        decimal.setStyleName("decimal");
        fraction.setStyleName("fraction");
        combinePrice.setStyleName("price_wrapper");
        combineName.setStyleName("name_wrapper");
        this.count.setDisplay(Display.NONE);
    }

    public void setProduct(String product) {
        name.setText(product);
    }

    public void setCount(String str) {
        Long count = toLong(str);
        if (count == 1) {
            this.count.setDisplay(Display.NONE);
        } else {
            this.count.setDisplay(Display.INLINE);
            this.count.setText("x" + count);
        }
    }

    public void setPrice(String str) {
        Long price = toLong(str);
        decimal.setText(String.valueOf(price / 100));
        fraction.setText(NumberFormat.getFormat("#00").format(price % 100));
    }

    private Long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public void setCount(long count) {
        setCount(String.valueOf(count));
    }

    public void setPrice(long price) {
        setPrice(String.valueOf(price));
    }


}
