package kr.or.kids.domain.ca.user.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserForm {

    @NotBlank(message = "{validation.NotBlank.user.name}")
    @Size(min = 2, max = 20, message = "{validation.Size.user.name}")
    private String name;

    @NotBlank(message = "{validation.NotBlank.user.email}")
    @Email(message = "{validation.Email.user.email}")
    private String email;

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
