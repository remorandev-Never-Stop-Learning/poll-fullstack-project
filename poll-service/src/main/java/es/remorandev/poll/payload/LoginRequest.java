package es.remorandev.poll.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private  String usernameOrPassword;

    @NotBlank
    private String password;

    public String getUsernameOrPassword() {
        return usernameOrPassword;
    }

    public void setUsernameOrPassword(String usernameOrPassword) {
        this.usernameOrPassword = usernameOrPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
