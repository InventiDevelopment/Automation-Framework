package cz.inventi.qa.framework.core.data.config;

import cz.inventi.qa.framework.core.data.enums.ProxyScheme;
import cz.inventi.qa.framework.core.objects.framework.Log;

import java.util.Objects;

public class ProxySettings {
    private final String proxyServer;
    private final int proxyPort;
    private final ProxyScheme proxyScheme;
    private String proxyUser;
    private String proxyPass;

    public ProxySettings(String proxyUser, String proxyPass, String proxyServer, int proxyPort, ProxyScheme proxyScheme) {
        Log.info("Setting framework proxy server to '" + proxyServer + ":" + proxyPort + "'");
        this.proxyServer = proxyServer;
        this.proxyPort = proxyPort;
        this.proxyScheme = Objects.requireNonNullElse(proxyScheme, ProxyScheme.HTTP);
        Log.info("Setting proxy scheme to '" + this.proxyScheme + "'");

        if (proxyUser != null && proxyPass != null) {
            Log.info("Proxy authentication supplied for user '" + proxyUser + "'");
            this.proxyUser = proxyUser;
            this.proxyPass = proxyPass;
        }
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public String getProxyServer() {
        return proxyServer;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public ProxyScheme getProxyScheme() {
        return proxyScheme;
    }
}
