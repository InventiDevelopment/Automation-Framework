package cz.inventi.qa.framework.core.objects.parameters;

/**
 * Class to store authorization parameters.
 */
public class AuthParameters {
    private String username;
    private String password;
    private String authToken;
    private String apiKey;

    public AuthParameters setUsername(String username) {
        this.username = username;
        return this;
    }

    public AuthParameters setPassword(String password) {
        this.password = password;
        return this;
    }

    public AuthParameters setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public AuthParameters setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getApiKey() {
        return apiKey;
    }
}
