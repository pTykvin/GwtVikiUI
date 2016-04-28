package server.entities;

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
