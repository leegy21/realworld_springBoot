package GDSC.realWorld.service;

import GDSC.realWorld.domain.UserDTO;
import GDSC.realWorld.entity.User;
import GDSC.realWorld.exception.UserNotFoundException;
import GDSC.realWorld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

    public Member login(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }
    
    public Member getMemberByEmailAndPassword(String email, String password) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getMemberByEmailAndPassword'");
    }
    
    public void followUser(String usernameToFollow, String followerUsername) {
        User userToFollow = findByUsername(usernameToFollow);
        User follower = findByUsername(followerUsername);
    
        follower.getFollowing().add(userToFollow);
        save(follower);
    }

    public void unfollowUser(String usernameToUnfollow, String followerUsername) {
        User userToUnfollow = findByUsername(usernameToUnfollow);
        User follower = findByUsername(followerUsername);
    
        follower.getFollowing().remove(userToUnfollow);
        save(follower);
    }

    
}