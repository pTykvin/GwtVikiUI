package server.keyboard;

public class KeyCode {
    private final char keyCode;
    private final long number;

    public KeyCode(char keyCode, long number) {
        this.keyCode = keyCode;
        this.number = number;
    }

    public char getKeyCode() {
        return keyCode;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "KeyCode{" +
            "keyCode=" + keyCode +
            ", number=" + number +
            '}';
    }
}
