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

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;

import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialModalContent;
import gwt.material.design.client.ui.html.Div;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.viki.client.VikiResources;
import gwt.material.design.viki.client.share.dialog.simple.DialogAction;
import gwt.material.design.viki.client.share.dialog.simple.ShowSimpleDialogTask;
import gwt.material.design.viki.client.share.dialog.simple.SimpleDialogInfo;

public class VikiDialogSimple extends VikiDialog<ShowSimpleDialogTask, SimpleDialogInfo> {
    // UI Elements
    private MaterialImage image;
    private Label description;
    private Label caption;
    private VikiButton leftButton;
    private VikiButton rightButton;
    // Wrappers for layout
    private MaterialModalContent modalContent;
    private Div body;
    private Div footer;
    private HandlerRegistration rightHandler;
    private HandlerRegistration leftHandler;

    public VikiDialogSimple(ShowSimpleDialogTask anEvent) {
        super(anEvent);
        SimpleDialogInfo info = anEvent.getInfo();
        addStyleName(info.getStyle());
        setImage((ImageResource) VikiResources.INSTANCE.getResource(info.getImageName()));
        setCaption(info.getCaption());
        setDescription(info.getDescription());
        registerRightAction(anEvent.getRightAction());
        registerLeftAction(anEvent.getLeftAction());
    }

    @Override
    protected void construct() {
        image = new MaterialImage();
        description = new Label();
        caption = new Label();
        leftButton = VikiButton.build(task.getLeftAction());
        rightButton = VikiButton.build(task.getRightAction());
        modalContent = new MaterialModalContent();
        body = new Div();
        footer = new Div();

        body.add(image);
        body.add(description);
        body.add(caption);

        addButton(leftButton);
        addButton(rightButton);

        modalContent.add(body);
        modalContent.add(footer);

        add(modalContent);
    }

    private void addButton(VikiButton button) {
        if (button != null) {
            footer.add(button);
        }
    }

    @Override
    protected void registerProperties(ShowSimpleDialogTask task) {

    }

    @Override
    protected void unregisterProperties(ShowSimpleDialogTask task) {

    }

    @Override
    protected void stylize() {
        addStyleName("viki-dialog");
        modalContent.addStyleName("viki-dialog-content");
        body.addStyleName("viki-dialog-body");
        description.addStyleName("viki-dialog-description");
        caption.addStyleName("viki-dialog-caption");
        footer.addStyleName("viki-dialog-footer");

    }

    public void setImage(ImageResource image) {
        this.image.setResource(image);
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public void setCaption(String caption) {
        this.caption.setText(caption);
    }

    public void registerLeftAction(DialogAction leftAction) {
        releaseHandler(leftHandler);
        leftHandler = addHandler(leftButton, leftAction);
    }

    public void registerRightAction(DialogAction rightAction) {
        releaseHandler(rightHandler);
        rightHandler = addHandler(rightButton, rightAction);
    }

}
