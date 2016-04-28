package server.entities;

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


import gwt.material.design.viki.client.share.dialog.base.VikiBooleanClientProperty;
import server.ServerStub;

public class PropertyHandler {
    private static ServerStub server;

    public static void start(ServerStub serverStub) {
        server = serverStub;
    }

    public static void registerBooleanProperty(final VikiBooleanProperty serverProperty, final VikiBooleanClientProperty clientProperty) {
        serverProperty.setListener(b -> {
            clientProperty.setValue(b);
            server.fireBooleanProperty(clientProperty);
        });
    }
}
