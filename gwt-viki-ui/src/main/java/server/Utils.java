package server;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static String encode(String src) {
        if (src == null)
            return null;
        return new String(src.getBytes(), Charset.forName(StandardCharsets.UTF_8.name()));
    }
}
