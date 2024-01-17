package GDSC.realWorld.domain;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String image = "https://realworld-temp-api.herokuapp.com/images/smiley-cyrus.jpeg";
    private String bio;
    private boolean demo = false;

}
