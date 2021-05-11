package cz.inventi.qa.framework.core.objects.parameters;

/**
 * Class to store authorization parameters.
 */
public class AuthParameters {
    private String username;
    private String password;
    private String authToken;
    private String apiKey;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
