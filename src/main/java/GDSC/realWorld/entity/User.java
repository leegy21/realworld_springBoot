package GDSC.realWorld.entity;

import java.util.ArrayList;
import java.util.List;
import GDSC.realWorld.domain.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String image = "https://realworld-temp-api.herokuapp.com/images/smiley-cyrus.jpeg";
    private String bio;
    @Column(nullable = false)
    private boolean demo = false;

    public User(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    private List<User> following = new ArrayList<>();
    //DB에는 List 형식의 열을 생성하지 못함

    public List<User> getFollowing() {
        return following;
    }

    public void addFollowing(User user) {
        following.add(user);
    }

    public void removeFollowing(User user) {
        following.remove(user);
    }

    public User(String email2, String password2) {
        //TODO Auto-generated constructor stub
    }

}
