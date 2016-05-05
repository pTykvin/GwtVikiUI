package gwt.material.design.viki.client.utils;

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


import com.google.gwt.dom.client.Element;

import client.dialog.base.VikiBooleanClientProperty;

public class JsUtils {

    public static native void sendStringKey(int entityId, String string) /*-{
        $wnd.sendStringKey(entityId, string);
    }-*/;

    public static native void sendKeyCode(int entityId, int key) /*-{
        $wnd.sendKeyCode(entityId, key);
    }-*/;

    public static native void fireString(String content)/*-{
        if ($wnd.vikiFocused) {
            $wnd.vikiFocused.innerText = content;
        }
    }-*/;

    public static native void customDialogEvent(int entityId, int eventId) /*-{
        if (entityId == 0) {
            alert('Entity not registred!');
        } else if (eventId == 0) {
            alert('Event not registred!');
        } else {
            $wnd.dialogEvent(entityId, eventId);
        }
    }-*/;

    public static native void closeVikiDialog() /*-{
        $wnd.closeVikiDialog();
    }-*/;

    public static native void bindDestination(Element destination) /*-{
        $wnd.vikiFocused = destination;
    }-*/;

    public static native void fireBooleanProperty(Element e, VikiBooleanClientProperty anEvent) /*-{
        $(e).trigger('onBooleanPropertyChange', anEvent);
    }-*/;
}
