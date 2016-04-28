package server.entities;

public class VikiBooleanProperty extends VikiProperty<Boolean> {
    protected VikiBooleanProperty(Boolean value) {
        super(value);
    }

    public static VikiBooleanProperty of(Boolean value) {
        return new VikiBooleanProperty(value);
    }
}
