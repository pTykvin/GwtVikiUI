package server.entities;

import java.util.concurrent.atomic.AtomicInteger;

import gwt.material.design.viki.client.share.dialog.base.Action;
import gwt.material.design.viki.client.share.dialog.base.VikiBooleanClientProperty;

public abstract class Shadow<C extends Action, S extends ServerAction> {

    private final int entityId;

    private final AtomicInteger idPropertyGenerator = new AtomicInteger();

    public Shadow(int entityId) {
        this.entityId = entityId;
    }

    public abstract void apply(int eventId);

    public abstract boolean isEntityKiller(int eventId);

    public abstract C registerEvent(S serverEvent);


    protected VikiBooleanClientProperty createClientBooleanProperty(VikiBooleanProperty vikiBooleanProperty) {
        VikiBooleanClientProperty property = new VikiBooleanClientProperty();
        int propertyId = idPropertyGenerator.incrementAndGet();
        property.setPropertyId(propertyId);
        property.setEntityId(entityId);
        property.setValue(vikiBooleanProperty.get());
        PropertyHandler.registerBooleanProperty(vikiBooleanProperty, property);
        return property;
    }
}
