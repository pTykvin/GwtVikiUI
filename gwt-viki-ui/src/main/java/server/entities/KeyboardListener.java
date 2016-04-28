package server.entities;

public interface KeyboardListener {
    void applyKey(int keycode);

    void applyString(String key);
}
