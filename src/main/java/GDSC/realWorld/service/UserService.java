package GDSC.realWorld.service;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.entity.Follow;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.UserNotFoundException;
import GDSC.realWorld.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserDTO userDTO) {
        User user = Optional.ofNullable(userRepository.findUserByEmail(userDTO.getEmail()))
                .orElseThrow(UserNotFoundException::new);
        user.setEmail(userDTO.getEmail());
        user.setBio(userDTO.getBio());
        user.setImage(userDTO.getImage());
        return user;
    }

    public User findByUsername(String username) {
        return Optional.ofNullable(userRepository.findUserByUsername(username))
                .orElseThrow(UserNotFoundException::new);

    }

    public User login(String email, String password) {
        User user = userRepository.findUserByEmail(email);

        if(user == null){
            throw new UserNotFoundException();
        }

        return user;
    }
    
    public User getMemberByEmailAndPassword(String email, String password) {
            User user = userRepository.findUserByEmail(email);

            if (user != null && passwordEncoder.matches(password, user.getPassword())){
                    return user;
            } else {
                return null;
            }
    }

    
    public void followUser(String usernameToFollow, String followerUsername) {
        User userToFollow = findByUsername(usernameToFollow);
        User follower = findByUsername(followerUsername);
    
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(userToFollow);
    

        follower.getFollowing().add(follow);
        save(follower);
    }

    public void unfollowUser(String usernameToUnfollow, String followerUsername) {
        User userToUnfollow = findByUsername(usernameToUnfollow);
        User follower = findByUsername(followerUsername);

        follower.getFollowing().remove(userToUnfollow);
        save(follower);
    }

    public User getCurrentUser(HttpServletRequest request) {
        
        HttpSession session = request.getSession(false);

        if(session != null){
            User user = (User) session.getAttribute("currentUser");
            return user;
        } else {
            return null;
        }
    }
}