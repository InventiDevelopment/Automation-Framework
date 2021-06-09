package cz.inventi.qa.framework.core.data.enums;

import java.util.Arrays;

public enum ProxyScheme {
    HTTP,
    HTTPS;

    public static ProxyScheme fromString(String text) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}
