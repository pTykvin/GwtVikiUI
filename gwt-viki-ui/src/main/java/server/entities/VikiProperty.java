package server.entities;

import java.util.function.Consumer;

public abstract class VikiProperty<T> {

    private T value;
    private Consumer<T> listener;

    protected VikiProperty(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public boolean set(T value) {
        boolean changed = value != this.value;
        if (changed) {
            if (listener != null) {
                listener.accept(value);
            }
            this.value = value;
        }
        return changed;
    }

    public Consumer<T> getListener() {
        return listener;
    }

    void setListener(Consumer<T> listener) {
        this.listener = listener;
    }

    public void setInner(T value) {
        this.value = value;
    }
}
