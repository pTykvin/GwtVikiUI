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

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Br;
import gwt.material.design.client.ui.html.Div;

public class VikiKeyboard extends MaterialWidget {

    private final String firstRow = "йцукенгшщзх";
    private final String secondRow = "фывапролджэ";
    private final String thirdRow = "ячсмитьбю";
    private boolean show;

    public VikiKeyboard() {
        super(Document.get().createDivElement());
        stylize();
        construct();
    }

    public void show() {
        show = true;
        setTop(768 - 382);
    }

    public void hide() {
        show = false;
        setTop(768);
    }

    public void trig() {
        if (show) {
            hide();
        } else {
            show();
        }
    }

    private void construct() {
        addKeys(createRow(), firstRow);
        addKeys(createRow(), secondRow);
        addKeys(createRow(), thirdRow);
    }

    private Div createRow() {
        Div div = new Div();
        div.setStyleName("row");
        return div;
    }

    private void addKeys(Div rowElement, String rowContent) {
        char[] chars = rowContent.toCharArray();
        for (char aChar : chars) {
            MaterialLabel child = new MaterialLabel(String.valueOf(aChar));
            child.setWaves(WavesType.DEFAULT);
            child.addStyleName("center");
            child.setCircle(true);
            rowElement.add(child);
        }
        add(rowElement);
    }

    private void stylize() {
        setStyleName("viki-keyboard");
    }
}
